%% Draw error decreasing figure for bp 

filename = 'readDatabp(0.5l0.0m100i).txt';
data = importdata(filename);
num = length(data) - 1;
err = data(1:end-1);

figure, clf, 
plot(1:num, err, 'LineWidth', 2);
grid on, 
xlabel('Iteration i');
ylabel('Mean Square Error(mse) e');
title('Mean Square Error(mse) for BP Netwrok');
axis([0, inf, 0, 5000]);