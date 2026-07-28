# 🌹 Sua Essência — Catálogo de Perfumes

> Catálogo digital para a perfumaria **Sua Essência** — desenvolvido com HTML/CSS/JS, Spring Boot, MySQL e Docker.

---

## 📱 Demonstração

| Página | Descrição |
|--------|-----------|
| `/index.html` | Catálogo público com filtros e busca |
| `/pages/produto.html` | Página de detalhe do produto com galeria |
| `/pages/quem-somos.html` | História e valores da marca |
| `/pages/contato.html` | Formulário de contato via WhatsApp |
| `/pages/admin.html` | Dashboard administrativo (requer login) |

---

## 🗂️ Estrutura do Projeto

```
suaessencia/
├── frontend/
│   ├── index.html               ← Catálogo público
│   ├── css/
│   │   ├── global.css           ← Design system (cores, navbar, footer)
│   │   └── catalog.css          ← Estilos do catálogo e cards
│   ├── js/
│   │   ├── api.js               ← Comunicação com o backend (JWT + REST)
│   │   ├── catalog.js           ← Lógica do catálogo, filtros e busca
│   │   └── nav.js               ← Menu mobile
│   └── pages/
│       ├── produto.html         ← Página de detalhe com galeria de fotos
│       ├── quem-somos.html      ← Página institucional
│       ├── contato.html         ← Fale conosco via WhatsApp
│       └── admin.html           ← Dashboard admin protegido por JWT
│
├── backend/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/java/com/suaessencia/backend/
│       ├── BackendApplication.java
│       ├── controller/
│       │   ├── ProdutoController.java   ← Endpoints REST
│       │   └── AuthController.java      ← Login JWT
│       ├── model/
│       │   └── Produto.java             ← Entidade JPA
│       ├── dto/
│       │   ├── ProdutoDTO.java
│       │   └── AuthDTO.java
│       ├── repository/
│       │   └── ProdutoRepository.java
│       ├── service/
│       │   └── ProdutoService.java
│       ├── security/
│       │   ├── JwtUtil.java
│       │   └── JwtFilter.java
│       └── config/
│           ├── SecurityConfig.java
│           ├── GlobalExceptionHandler.java
│           ├── AdminUserStore.java
│           └── DataInitializer.java
│
├── docker-compose.yml           ← MySQL + Backend juntos
├── .gitignore
└── README.md
```

---

## ✨ Funcionalidades

### Catálogo Público
- Grid responsivo de produtos com foto, nome e preço
- **Página de detalhe** com galeria de até 3 fotos e navegação por setas
- Filtro por **categoria** (Árabe, Importados, Body Splash, Maquiagem, Miniaturas)
- Filtro por **gênero** (Feminino, Masculino, Unissex)
- Filtro por **Mais Vendidos** e **Promoções**
- Barra de **busca** por nome e descrição
- Botão **"Finalizar no WhatsApp"** com mensagem automática do produto
- Página **Quem Somos** institucional
- Página **Fale Conosco** com formulário que abre o WhatsApp

### Dashboard Admin
- Login seguro com **JWT** (token de 24 horas)
- Estatísticas: total de produtos, disponíveis, indisponíveis e promoções
- **Adicionar** produto com nome, categoria, gênero, preço, preço antigo, descrição e até 3 imagens
- **Editar** produto existente
- **Ativar/Desativar** disponibilidade sem excluir
- **Excluir** produto
- Busca e filtros na tabela de produtos
- Produtos indisponíveis somem do catálogo mas continuam visíveis no admin

---

## 🎨 Paleta de Cores

| Nome | Hex | Uso |
|------|-----|-----|
| Vinho | `#5C1520` | Primária, navbar, botões |
| Dourado | `#C9A84C` | Acentos, logo, destaques |
| Bronze | `#9A6B3A` | Texto secundário, categorias |
| Areia | `#F5EFE4` | Fundo principal |
| Bege | `#E8DCC8` | Bordas, fundos alternativos |

---

## 🛠️ Tecnologias

| Camada | Tecnologia |
|--------|-----------|
| Frontend | HTML5, CSS3, JavaScript (Vanilla) |
| Backend | Java 17, Spring Boot 3.4.1 |
| Segurança | Spring Security + JWT (jjwt 0.12.6) |
| Banco de Dados | MySQL 8.0 |
| ORM | Hibernate / Spring Data JPA |
| Containerização | Docker + Docker Compose |
| Imagens | Cloudinary |
| Deploy Backend | Railway |
| Deploy Frontend | Vercel |

---

## 📡 Endpoints da API

| Método | Rota | Acesso | Descrição |
|--------|------|--------|-----------|
| `GET` | `/api/produtos` | Público | Lista produtos disponíveis |
| `GET` | `/api/produtos/{id}` | Público | Detalhe de um produto |
| `POST` | `/api/auth/login` | Público | Login, retorna token JWT |
| `GET` | `/api/produtos/admin/todos` | Admin | Lista todos (incl. inativos) |
| `POST` | `/api/produtos` | Admin | Cria produto |
| `PUT` | `/api/produtos/{id}` | Admin | Edita produto |
| `PATCH` | `/api/produtos/{id}/disponibilidade` | Admin | Ativa/desativa |
| `DELETE` | `/api/produtos/{id}` | Admin | Remove produto |

---

## ⚙️ Pré-requisitos

- Java 17
- Docker Desktop
- Maven 3.9+

---

## 🚀 Rodando Localmente

**1. Clone o repositório:**
```bash
git clone https://github.com/MiguelAlves05/suaessencia.git
cd suaessencia
```

**2. Crie o `application.properties`:**
```bash
cp backend/src/main/resources/application.example.properties \
   backend/src/main/resources/application.properties
```
Edite o arquivo com suas credenciais.

**3. Suba com Docker:**
```bash
docker-compose up --build
```

**4. Acesse o frontend:**

Abra `frontend/index.html` com o Live Server do VSCode em:
```
http://127.0.0.1:5500/frontend/index.html
```

**5. Dashboard admin:**
```
http://127.0.0.1:5500/frontend/pages/admin.html
```
- Usuário: `admin`
- Senha: definida no `application.properties`

---

## ☁️ Deploy em Produção

### Backend → Railway
1. Crie um projeto no [railway.app](https://railway.app)
2. Adicione o plugin MySQL
3. Configure as variáveis de ambiente:

| Variável | Valor |
|----------|-------|
| `SPRING_DATASOURCE_URL` | URL gerada pelo Railway |
| `SPRING_DATASOURCE_USERNAME` | Usuário MySQL do Railway |
| `SPRING_DATASOURCE_PASSWORD` | Senha MySQL do Railway |
| `JWT_SECRET` | Chave secreta longa e aleatória |
| `ADMIN_USERNAME` | admin |
| `ADMIN_PASSWORD` | Senha forte |
| `CORS_ALLOWED_ORIGINS` | URL do Vercel |
| `SPRING_PROFILES_ACTIVE` | prod |

### Frontend → Vercel
1. Importe o repositório no [vercel.com](https://vercel.com)
2. Defina o **Root Directory** como `frontend`
3. Em `js/api.js`, atualize:
```js
const API_BASE = 'https://SUA-URL.railway.app';
```

---

## 🔑 Segurança

- Senhas armazenadas com **BCrypt**
- Autenticação via **JWT** com expiração de 24h
- Rotas admin protegidas no backend — não apenas no frontend
- `application.properties` no `.gitignore` — nunca sobe ao GitHub
- CORS configurado para aceitar apenas origens autorizadas

---

## 👤 Autor

**Miguel Alves** — Estudante de Análise e Desenvolvimento de Sistemas

[![GitHub](https://img.shields.io/badge/GitHub-MiguelAlves05-181717?logo=github)](https://github.com/MiguelAlves05)

---

*© 2026 Sua Essência — Perfumaria Exclusiva*
