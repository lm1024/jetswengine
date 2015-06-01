d=dir('logfiles\*.csv');
[dx,dx]=sort([d.datenum], 'descend');
lf=d(dx==1).name;

fid = fopen(strcat('logfiles\', lf),'r');


C = textscan(fid, repmat('%s',1,10), 'delimiter',',', 'CollectOutput',true);
C = C{1};
fclose(fid);

clear title;
graphTitle = char(C(1,1));

numberOfAnswers = str2num(cell2mat(C(1,2)));

answerNames = cellstr(C(3:(numberOfAnswers+2),1));

answerData = str2double(C(3:(numberOfAnswers+2), 2));


figure
subplot(3,1,1)
set(gcf,'units','normalized','outerposition',[0 0 1 1])

bar(answerData, 0.3);

title(graphTitle, 'FontWeight', 'Bold')

ylabel('Number of responses', 'FontWeight', 'Bold') % y-axis label

set(gca, 'XTick', 1:4, 'XTickLabel', answerNames);

set(gca, 'ytick', 0:max(answerData));

times = str2double(C((numberOfAnswers+3):end, 1));
id = str2double(C((numberOfAnswers+3):end, 2));
answers = str2double(C((numberOfAnswers+3):end, 3));

%deltaTime = deltaTime/1000;

labels2 = cell(length(answers),1);

labelsNum = 0:length(answers)-1;

subplot(3,1,2)

r = zeros(max(id));
g = zeros(max(id));
b = zeros(max(id));


hold on

for i = 0:max(id)
    c = [rand, rand, rand];
    r(i+1) = c(1);
    g(i+1) = c(2);
    b(i+1) = c(3);
    if (isequal(C, [1,1,1]))
        c = [0,0,0];
    end
    for j = 1:length(id)
       if (i == id(j)) 
           plot((times(j)-times(1))/1000000000, answers(j), '--gs',...
            'LineWidth',2,...
            'MarkerSize',10,...
            'MarkerEdgeColor',c,...
            'MarkerFaceColor',c);
       end    
    end
end

sort(labelsNum);

set(gca, 'YTick', 0:3, 'YTickLabel', answerNames);

axis([0, ceil((times(j)-times(1))/1000000000), 0, numberOfAnswers]);

xlabel('Time from initial response (seconds)', 'FontWeight', 'Bold') % x-axis label
ylabel('Answers', 'FontWeight', 'Bold') % y-axis label
title('Responses to Question Against Time', 'FontWeight', 'Bold');

subplot(3,1,3)

hold on

for i = 0:max(id)
    c = [r(i+1), g(i+1), b(i+1)];
    
    if (isequal(C, [1,1,1]))
        c = [0,0,0];
    end
    
    lastAnswerTime = 0;
    lastJ = 1;
    
    for j = 1:length(id)
       if (i == id(j)) 
           
           if ((times(j)-times(1))/1000000000 > lastAnswerTime)
                lastAnswerTime = (times(j)-times(1))/1000000000;
                lastJ = j;
           end
           
       end    
    end
     plot(lastAnswerTime, answers(lastJ), '--gs',...
            'LineWidth',2,...
            'MarkerSize',10,...
            'MarkerEdgeColor',c,...
            'MarkerFaceColor',c);
    
end

sort(labelsNum);

set(gca, 'YTick', 0:3, 'YTickLabel', answerNames);

axis([0, ceil((times(j)-times(1))/1000000000), 0, numberOfAnswers]);

xlabel('Time from initial response (seconds)', 'FontWeight', 'Bold') % x-axis label
ylabel('Answers', 'FontWeight', 'Bold') % y-axis label
title('Final Responses to Question Against Time', 'FontWeight', 'Bold');



d=dir('receivedQuestions\*.csv');

if (size(dir) > 0)
    num = length(d(not([d.isdir])));
    for (i = 1:num)
        fid = fopen(strcat('receivedQuestions\' ,d(i).name));  
        data = fread(fid, '*char')'
        fclose(fid);
    end
end





