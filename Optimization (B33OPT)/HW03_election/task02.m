load('volby_2017.mat');

[U, S, V] = svd(T.data' - (sum(T.data', 2) / size(T.data', 2)));
result = T.data * U(:, 1:2);

figure
hold on
title('Spektrum otazek podle nejcastejsi odpovedi')
scatter(result(:, 1), result(:, 2), 100, sum(T.data'==1), 'filled');
text(result(:, 1), result(:, 2), T.questions);
hold off