%% Draw Step Function and Sigmoid Function
x = -10:0.1:10;
y = zeros(1, length(x));
for i = 1:length(x)
    if x(i) >= 0
        y(i) = 1;
    end
end

figure(1), clf, 
plot(x, y, 'LineWidth', 2);
grid on,
xlabel('x');
ylabel('y');
title('Step Function');

y = logsig(x);
figure(2), clf, 
plot(x, y, 'LineWidth', 2);
grid on,
xlabel('x');
ylabel('y');
title('Sigmoid Function');