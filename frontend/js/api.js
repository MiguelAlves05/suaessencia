/* =============================================
   API.JS — Comunicação com o Backend
   ============================================= */

const API_BASE = 'https://suaessencia-api.onrender.com';

// Token JWT (admin)
function getToken() { return localStorage.getItem('se_token'); }
function setToken(t) { localStorage.setItem('se_token', t); }
function removeToken()  { localStorage.removeItem('se_token'); }

async function apiFetch(path, options = {}) {
  const headers = { 'Content-Type': 'application/json', ...(options.headers || {}) };
  const token = getToken();
  if (token) headers['Authorization'] = 'Bearer ' + token;

  const res = await fetch(API_BASE + path, { ...options, headers });
  if (!res.ok) {
    const err = await res.json().catch(() => ({ message: 'Erro desconhecido' }));
    throw new Error(err.message || `HTTP ${res.status}`);
  }
  if (res.status === 204) return null;
  return res.json();
}

/* ── PRODUTOS ── */
const ProdutosAPI = {
  listar: ()            => apiFetch('/api/produtos'),
  buscar: (id)          => apiFetch(`/api/produtos/${id}`),
  criar:  (data)        => apiFetch('/api/produtos', { method: 'POST', body: JSON.stringify(data) }),
  atualizar: (id, data) => apiFetch(`/api/produtos/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
  deletar: (id)         => apiFetch(`/api/produtos/${id}`, { method: 'DELETE' }),
};

/* ── AUTH ── */
const AuthAPI = {
  login: async (username, password) => {
    const data = await apiFetch('/api/auth/login', {
      method: 'POST',
      body: JSON.stringify({ username, password })
    });
    setToken(data.token);
    return data;
  },
  logout: () => removeToken(),
  isLoggedIn: () => !!getToken(),
};
