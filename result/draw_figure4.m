%% Draw the correct rate 

% simple data 
path = 'easyRandom.txt';
data = importdata(path);
num = length(data);

h = figure(1);
figure(h), 
scatter(1: num, data, 'filled');
grid on, hold on, 
plot([0,10], [1, 1] * sum(data)/num, '--','LineWidth', 2);
xlabel('Trail Time n^{th}');
ylabel('Correct Rate r(%)');
title('Correct Rate with Dummy Samples');
axis([0, 10, 0, 100]);
legend('Each Trail', 'Average');

% Real data 
path = 'realDataRandom.txt';
data = importdata(path);
num = length(data);

a = figure(2);
figure(a), 
scatter(1: num, data, 'filled');
grid on, hold on, 
plot([0,10], [1, 1] * sum(data)/num, '--','LineWidth', 2);
xlabel('Trail Time n^{th}');
ylabel('Correct Rate r(%)');
title('Correct Rate with Digital Hand-writing Dataset');
axis([0, 10, 0, 100]);


path = 'readDatabp(0.5l0.3m10i)10.txt';
data = importdata(path);
num = length(data);

figure(a), 
scatter(1: num, data, 'filled');
grid on, hold on, 
plot([0,10], [1, 1] * sum(data)/num, '--','LineWidth', 2);
legend('Ranom Assignment', 'Average(Random)', 'BP Net', 'Average(BP)');
