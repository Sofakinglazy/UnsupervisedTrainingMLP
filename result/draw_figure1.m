%% 
startp = 0.3; 
stopp = 10;
step = 0.1;
momentums = [0.0, 0.3, 0.5, 0.8];

for count = 1:length(momentums)
    nums = {};
    for l = startp: step: stopp
        m = sprintf('%.1f',momentums(count));
        root = strcat('LearningRateBPM', m);
        filename = strcat(root, '/easybp(', sprintf('%.1f',l), 'l', m,'m).txt');
        err = importdata(filename);
        num = length(err) - 1;
        nums = [nums, num];
    end
    
    l = startp: step: stopp;
    y = cell2mat(nums);

    figure(1),  
    plot(l, y, 'LineWidth', 2);
    grid on, grid minor, hold on,
end
figure(1),
xlabel('Learning rate \eta');
ylabel('Iteration i');
title('Relationship of Learning Rate \eta and Iteration i ');
legend('Momentum:0.0', 'Momentum:0.3', 'Momentum:0.5', 'Momentum:0.8');
axis([0, 7, 0, inf]);
