%% Relationship of Err and Iteration Function
function [h] = err_iteration(path, f)
h = figure('Visible', 'off');
if (nargin > 1)
    h = f;
end

data = importdata(path);
num = length(data) - 1;
err = data(1:end-1);

% h.Visible = 'off';
figure(h),
plot(1:num, err, 'LineWidth', 2);
grid on, hold on, 
xlabel('Iteration i');
ylabel('Mean Square Error(mse) e');
title('Mean Square Error(mse) for BP Netwrok');
% axis([0, inf, 0, 5000]);


end