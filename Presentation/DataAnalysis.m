d=dir('*.csv');
[dx,dx]=sort([d.datenum], 'descend');
lf=d(dx==1).name

fid = fopen(lf,'r');
C = textscan(fid, repmat('%s',1,10), 'delimiter',',', 'CollectOutput',true);
C = C{1};
fclose(fid);

clear title;
graphTitle = char(C(1,1));

answerNames = cellstr(C(3:end,1));

answerData = str2double(C(3:end, 2));

bar(answerData, 0.3);

title(graphTitle)

set(gca, 'XTick', 1:4, 'XTickLabel', answerNames);