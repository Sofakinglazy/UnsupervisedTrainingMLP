%% Draw figure for BP network with real data

h = err_iteration('readDatabp(0.5l0.0m1000i).txt');
h.Visible = 'off';
h = err_iteration('readDatabp(0.5l0.0m1000i)2.txt', h);

figure(h), 
title('Mean Square Error(mse) for BP Netwrok with Real Data');
legend('100 iterations', '1000 iterations');
axis([0, 1000, 0, 1000]);