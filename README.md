# 🌹 Sua Essência — Catálogo de Perfumes

Catálogo digital para a perfumaria Sua Essência.  
Frontend em HTML/CSS/JS · Backend em Spring Boot · Banco de dados MySQL · Deploy com Docker, Railway e Vercel.

---

## 📁 Estrutura do Projeto

```
suaessencia/
├── frontend/
│   ├── index.html              ← Catálogo público
│   ├── css/
│   │   ├── global.css          ← Design system (cores, navbar, footer)
│   │   └── catalog.css         ← Estilos do catálogo
│   ├── js/
│   │   ├── api.js              ← Comunicação com o backend
│   │   ├── catalog.js          ← Lógica do catálogo
│   │   └── nav.js              ← Menu mobile
│   └── pages/
│       ├── quem-somos.html     ← Página Quem Somos
│       ├── contato.html        ← Página Fale Conosco
│       └── admin.html          ← Dashboard administrativo
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
└── docker-compose.yml          ← Ambiente local completo
```

---

## ⚙️ Pré-requisitos

- Java 21
- Maven 3.9+
- MySQL 8 (ou Docker Desktop)
- Node não é necessário — frontend é HTML puro

---

## 🚀 Rodando Localmente

### Opção A — Docker (mais fácil)

```bash
# Na raiz do projeto
docker-compose up --build

# Backend disponível em: http://localhost:8080
# Frontend: abra frontend/index.html no navegador
```

### Opção B — Manual

**1. Crie o banco MySQL:**
```sql
CREATE DATABASE suaessencia;
```

**2. Edite `backend/src/main/resources/application.properties`:**
```properties
spring.datasource.password=SUA_SENHA_AQUI
```

**3. Inicie o backend:**
```bash
cd backend
mvn spring-boot:run
```

**4. Abra o frontend:**
```
Clique duplo em frontend/index.html
ou use um servidor local: npx serve frontend
```

---

## 🔑 Credenciais Padrão

| Campo    | Valor     |
|----------|-----------|
| Usuário  | `admin`   |
| Senha    | `admin123`|

> ⚠️ **Mude a senha antes de ir para produção!** Edite `admin.password` no `application.properties` ou na variável de ambiente `ADMIN_PASSWORD`.

---

## 📡 Endpoints da API

| Método   | Rota                                    | Acesso  | Descrição                    |
|----------|-----------------------------------------|---------|------------------------------|
| `GET`    | `/api/produtos`                         | Público | Lista produtos disponíveis   |
| `GET`    | `/api/produtos/{id}`                    | Público | Detalhe de um produto        |
| `POST`   | `/api/auth/login`                       | Público | Login, retorna token JWT     |
| `GET`    | `/api/produtos/admin/todos`             | Admin   | Lista todos (incl. inativos) |
| `POST`   | `/api/produtos`                         | Admin   | Cria produto                 |
| `PUT`    | `/api/produtos/{id}`                    | Admin   | Edita produto                |
| `PATCH`  | `/api/produtos/{id}/disponibilidade`    | Admin   | Ativa/desativa produto       |
| `DELETE` | `/api/produtos/{id}`                    | Admin   | Remove produto               |

---

## ☁️ Deploy em Produção

### Backend → Railway

1. Crie uma conta em [railway.app](https://railway.app)
2. Crie um novo projeto → **Deploy from GitHub Repo**
3. Adicione o plugin **MySQL** ao projeto
4. Configure as variáveis de ambiente:
   ```
   SPRING_DATASOURCE_URL     = (Railway preenche automaticamente)
   SPRING_DATASOURCE_USERNAME = (Railway preenche automaticamente)
   SPRING_DATASOURCE_PASSWORD = (Railway preenche automaticamente)
   JWT_SECRET                 = MinhaChaveSuperSecreta2025
   ADMIN_USERNAME             = admin
   ADMIN_PASSWORD             = MinhaS3nhaForte!
   CORS_ALLOWED_ORIGINS       = https://suaessencia.vercel.app
   SPRING_PROFILES_ACTIVE     = prod
   ```
5. Deploy! Railway detecta o `Dockerfile` automaticamente.

### Frontend → Vercel

1. Crie uma conta em [vercel.com](https://vercel.com)
2. Importe o repositório GitHub
3. Defina o **Root Directory** como `frontend`
4. Em `js/api.js`, troque:
   ```js
   const API_BASE = 'https://SUA-APP.railway.app';
   ```
5. Deploy!

---

## 📱 Funcionalidades

### Catálogo Público
- [x] Grid responsivo de produtos com foto, nome, preço e descrição
- [x] Filtro por categoria na sidebar (Árabe, Importados, Body Splash, Maquiagem, Miniaturas)
- [x] Barra de busca por nome/descrição
- [x] Destaques e promoções com badges
- [x] Botão "Consultar no WhatsApp" por produto
- [x] Página Quem Somos
- [x] Página Fale Conosco (formulário → WhatsApp)

### Dashboard Admin
- [x] Login com JWT (sessão de 24 horas)
- [x] Estatísticas: total, disponíveis, indisponíveis, promoções
- [x] Tabela de todos os produtos com busca e filtros
- [x] Criar produto (nome, categoria, preço, preço antigo, descrição, imagem, flags)
- [x] Editar produto
- [x] Ativar/Desativar disponibilidade (sem excluir)
- [x] Excluir produto

---

## 🎨 Paleta de Cores

| Nome   | Hex       | Uso                          |
|--------|-----------|------------------------------|
| Vinho  | `#5c1520` | Primária, navbar, botões     |
| Dourado| `#c9a84c` | Acentos, logo, destaques     |
| Bronze | `#9a6b3a` | Texto secundário, categorias |
| Areia  | `#f5efe4` | Fundo principal              |
| Bege   | `#e8dcc8` | Bordas, fundos alternativos  |

---

## 📞 Suporte

Dúvidas ou ajustes? Fale com o desenvolvedor.
