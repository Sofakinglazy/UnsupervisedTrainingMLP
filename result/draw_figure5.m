%% Draw Block network weight difference between Iterations

% dummy samples
path = 'easyfl.txt';
h = err_iteration(path);

figure(h), 
ylabel('Mse of Weigths err');
title('Mean Square Error (Mse) of Weights between Each Iteration');
axis([0, 100, 0, 60]);

% real data 
path = 'realDatafl.txt';
a = err_iteration(path);

figure(a), 
ylabel('Mse of Weigths err');
title('Mean Square Error (Mse) of Weights between Each Iteration');
axis([0, 100, 0, 60]);