
# Expi 🤖
<img src="https://raw.githubusercontent.com/bruno1098/expi/main/public/logo.png" alt="Logo" width="90" align="left" />

✨ **ExpiKotlin** é um aplicativo Android desenvolvido em **Kotlin** com integração ao **Firebase** para gerenciar feedbacks de maneira eficiente. Ele permite que os usuários coletem, visualizem e gerenciem feedbacks em tempo real por meio de uma interface intuitiva e interativa.

---

## 🚀 Funcionalidades

- 🧑‍💻 **Autenticação de Usuário**: Crie uma conta ou faça login para acessar o dashboard personalizado.
- 📊 **Dashboard Interativo**: Visualize uma lista de feedbacks e gráficos para entender as avaliações recebidas.
- 💬 **Gerenciamento de Feedbacks**: Adicione feedbacks e veja detalhes dos feedbacks já existentes.
- 🔗 **Integração com Firebase**: Dados armazenados e sincronizados em tempo real com o **Firebase Realtime Database**.

---

## 🛠️ Tecnologias Utilizadas

Aqui estão as principais tecnologias e ferramentas utilizadas no desenvolvimento do DashKotlin:

- ![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white) **Kotlin**: Linguagem principal de programação utilizada.
- ![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black) **Firebase Realtime Database**: Utilizado para armazenar e sincronizar feedbacks em tempo real.
- ![Android Studio](https://img.shields.io/badge/Android_Studio-3DDC84?style=for-the-badge&logo=android-studio&logoColor=white) **Android Studio**: Ambiente de desenvolvimento integrado (IDE).
- ![MPAndroidChart](https://img.shields.io/badge/MPAndroidChart-555555?style=for-the-badge&logo=android&logoColor=white) **MPAndroidChart**: Biblioteca usada para a criação de gráficos no dashboard.

---

## ⚙️ Configuração do Firebase

O DashKotlin utiliza o **Firebase Realtime Database** para armazenar e sincronizar feedbacks. O arquivo `google-services.json` configura a conexão do app com o Firebase, incluindo a URL do banco de dados e as chaves de API necessárias.

### 🔑 Configuração Básica

- **URL do Banco de Dados**: `https://dashkt-5-default-rtdb.firebaseio.com`
- **Bucket de Armazenamento**: `dashkt-5.appspot.com`
- **ID do Projeto**: `dashkt-5`

### Conexão com Firebase

A conexão ao banco de dados é estabelecida dentro das activities principais, como no exemplo abaixo:

```kotlin
database = FirebaseDatabase.getInstance().getReference("usuario")
```

Esse comando conecta a activity ao nó "usuario" do Firebase, permitindo operações de leitura e gravação.

---

## 📝 Layouts das Activities

Os layouts foram projetados para proporcionar uma experiência de usuário amigável e fluida. Abaixo estão alguns destaques dos layouts:

- **DashboardActivity**: Exibe os feedbacks e o gráfico interativo.
- **FeedbackDetailActivity**: Mostra detalhes dos feedbacks, incluindo avaliações e data.
- **HomeActivity**: Tela inicial com as opções de login e registro.
- **LoginActivity**: Tela para que os usuários façam login com e-mail e senha.
- **RegisterActivity**: Tela de registro para novos usuários.

Todos os layouts seguem um design minimalista e moderno, com cores suaves e componentes de fácil interação, como botões arredondados e campos de entrada com bordas suaves.

---

## 📊 Diagramas do Projeto

Nesta seção, estão incluídos os principais diagramas que explicam a estrutura e o fluxo de uso do aplicativo:

### 1. **Diagrama de Estrutura de Pastas**

Esse diagrama mostra a organização das pastas e arquivos no projeto Android:

![Folder Structure Diagram](https://github.com/bruno1098/DashKotlin/blob/main/app/src/main/res/images/diagramPastas.png?raw=true)

### 2. **Diagrama de Fluxo de Utilização**

Este diagrama ilustra como os usuários interagem com o aplicativo, desde o login até a gestão de feedbacks:

![User Flow Diagram](https://github.com/bruno1098/DashKotlin/blob/main/app/src/main/res/images/DiagramaUtilizacao.png?raw=true)

---

## 🎨 Design do Aplicativo

O design do DashKotlin segue uma abordagem **minimalista** e **moderna**:

- **Cores Suaves**: A paleta de cores foi escolhida para garantir uma navegação visualmente confortável.
- **Componentes Arredondados**: Botões, campos de entrada e gráficos possuem bordas arredondadas para um toque mais suave e amigável.
- **Gráficos Interativos**: A biblioteca **MPAndroidChart** foi utilizada para exibir gráficos de pizza no dashboard, mostrando visualmente as avaliações dos feedbacks.

---

## 🔒 Segurança

O Firebase Realtime Database está configurado com regras de segurança que garantem que apenas usuários autenticados possam acessar ou modificar os dados. Isso protege as informações dos feedbacks e das contas dos usuários.

---

## 📌 Observações Finais

- **Arquivos Ocultos**: Para visualizar arquivos como `google-services.json` e outros arquivos importantes, mude para a aba "Project" no Android Studio.

---
