
% FindU - Sistema de Achados e Perdidos

% Objetos perdidos
perdido(carteira, acessorios, biblioteca).
perdido(chave, acessorios, laboratorio).
perdido(caderno, material_escolar, sala_101).
perdido(celular, eletronicos, cantina).

% Objetos encontrados
encontrado(carteira, acessorios, biblioteca).
encontrado(chave, acessorios, laboratorio).
encontrado(mochila, acessorios, recepcao).
encontrado(celular, eletronicos, cantina).

% Regra 1 - Possiveis correspondencias
possivel_correspondencia(Objeto) :-
    perdido(Objeto, Categoria, _),
    encontrado(Objeto, Categoria, _).

% Regra 2 - Encontrados sem registro de perda
sem_registro_de_perda(Objeto) :-
    encontrado(Objeto, _, _),
    \+ perdido(Objeto, _, _).
