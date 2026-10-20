# FindU — Sistema de Achados e Perdidos

Projeto desenvolvido para a disciplina de **Paradigmas de Linguagem de Programação**, do curso de Engenharia de Software da UniCesumar.

## Sobre o projeto

O **FindU** é um sistema de achados e perdidos voltado ao ambiente universitário. Seu objetivo é facilitar o cadastro, a consulta e a identificação de possíveis correspondências entre objetos perdidos e encontrados.

O sistema foi desenvolvido como parte do projeto integrador **Paradigmas em Ação**, demonstrando a aplicação de diferentes paradigmas de programação em uma mesma solução.

##  Tecnologias utilizadas

- **Java 21** — implementação do sistema.
- **Java Swing** — interface gráfica.
- **Java Streams** — pesquisa e filtragem de registros.
- **SWI-Prolog** — fatos, regras e consultas lógicas.
- **Visual Studio Code** — ambiente de desenvolvimento.

##  Paradigmas aplicados

| Paradigma | Aplicação no FindU |
|-----------|--------------------|
| Orientação a objetos | Modelagem de usuários, objetos e registros por meio de classes. |
| Programação funcional | Pesquisa e filtragem utilizando Streams e expressões lambda. |
| Programação lógica | Identificação de possíveis correspondências com fatos e regras em Prolog. |
| Orientação a eventos | Tratamento de ações dos usuários na interface Java Swing. |

##  Funcionalidades

- Cadastro de usuários.
- Registro de objetos perdidos e encontrados.
- Consulta e pesquisa de objetos cadastrados.
- Identificação de possíveis correspondências.
- Visualização de notificações.
- Registro de devolução de objetos.

## Estrutura do projeto

```text
FindU/
├── src/
│   ├── Main.java
│   ├── model/
│   ├── service/
│   └── view/
├── prolog/
│   └── findu.pl
├── docs/
│   ├── diagramas/
│   └── evidencias/
├── README.md
└── Relatório técnico (PDF)
```

## Como executar

### Aplicação Java

**Pré-requisito:** Java JDK 21 instalado.

1. Abra a pasta do projeto no Visual Studio Code.
2. Acesse `src/Main.java`.
3. Execute a classe `Main`.
4. Utilize a interface gráfica para testar as funcionalidades do FindU.

### Programação lógica

**Pré-requisito:** SWI-Prolog instalado.

1. Abra o arquivo `prolog/findu.pl` no SWI-Prolog.
2. Carregue a base de conhecimento.
3. Execute consultas como:

```prolog
possivel_correspondencia(Objeto).
sem_registro_de_perda(Objeto).
```

##  Documentação

O repositório contém o relatório técnico em PDF, o diagrama de classes UML e as evidências dos testes realizados.

Os materiais complementares estão organizados na pasta `docs/`.

## Integrantes

- Bruno Laroca
- Zoltan Goezce
- Jefferson Namur
- Severo Junior
- Luiz Bolzani

---

**Instituição:** UniCesumar  
**Curso:** Engenharia de Software  
**Turma:** ESOFT6SNA  
**Ano:** 2026
