%% Draw changes in weights between each sample 

data = importdata('realData(2,2,1)2.mat');
num = size(data, 1);

h = figure;

for i = 1:3
figure(h), 
plot(1:50, data(1:50, i)', 'LineWidth', 1);
grid on, hold on, 
end

figure(h), 
xlabel('The Sequence of Sameples n^{th}');
ylabel('Weight w');
title('Wights Changes with Samples (Decay Rate 0.2)');
legend('W_{11}', 'W_{21}', 'W_{31}');