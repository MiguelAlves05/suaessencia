/* =============================================
   CATALOG.JS — Lógica do Catálogo Público
   ============================================= */

const WHATSAPP = '5516981840032'; // 

let todosOsProdutos = [];
let catAtiva = 'todos';
let filterAtivo = null;
let generoAtivo = 'todos';

/* ── INICIALIZAÇÃO ── */
document.addEventListener('DOMContentLoaded', async () => {
  await carregarProdutos();
  iniciarFiltros();
  iniciarBusca();
});

async function carregarProdutos() {
  const grid = document.getElementById('productsGrid');
  grid.innerHTML = `
    <div style="grid-column:1/-1; text-align:center; padding:60px 0; color:var(--texto-lt)">
      <div style="font-size:2rem; margin-bottom:12px">🌸</div>
      <p style="font-size:.9rem">Carregando produtos...</p>
      <p style="font-size:.78rem; margin-top:6px; opacity:.6">Aguarde um momento</p>
    </div>`;
  try {
    todosOsProdutos = await ProdutosAPI.listar();
  } catch {
    todosOsProdutos = [];
  }
  renderizar(todosOsProdutos);
}



/* ── RENDER ── */
function renderizar(lista) {
  const grid = document.getElementById('productsGrid');
  const emptyState = document.getElementById('emptyState');
  const count = document.getElementById('resultsCount');

  if (lista.length === 0) {
    grid.innerHTML = '';
    emptyState.style.display = 'block';
    count.textContent = '';
    return;
  }
  emptyState.style.display = 'none';
  count.textContent = `${lista.length} produto${lista.length !== 1 ? 's' : ''}`;

  grid.innerHTML = lista.map((p, i) => cardHTML(p, i)).join('');
}

function cardHTML(p, index) {
  const fmtPreco = v => 'R$ ' + parseFloat(v).toFixed(2).replace('.', ',');
  const delay = Math.min(index * 40, 400);

  const badges = [
    p.destaque ? `<span class="badge badge-destaque">★ Destaque</span>` : '',
    p.promocao  ? `<span class="badge badge-promo">Promoção</span>` : '',
  ].filter(Boolean).join('');

  const precoHTML = p.precoAnt
    ? `<span class="preco-ant">${fmtPreco(p.precoAnt)}</span>${fmtPreco(p.preco)}`
    : fmtPreco(p.preco);

  const imgHTML = (p.imagemUrl || p.imagem)
    ? `<img src="${p.imagemUrl || p.imagem}" alt="${p.nome}" loading="lazy"
        onerror="this.parentElement.innerHTML='<div class=\\'card-img-placeholder\\'>🌸</div>'">`
    : `<div class="card-img-placeholder">🌸</div>`;

  return `
    <div class="card" style="animation-delay:${delay}ms" onclick="abrirProduto(${p.id})" style="cursor:pointer">
      ${badges ? `<div class="card-badges">${badges}</div>` : ''}
      <div class="card-img">${imgHTML}</div>
      <div class="card-body">
        <div class="card-cat">${p.categoria || ''}</div>
        <div class="card-nome">${p.nome}</div>
        <div class="card-preco">${precoHTML}</div>
      </div>
    </div>`;
}

function abrirProduto(id) {
  window.location.href = `pages/produto.html?id=${id}`;
}
/* ── FILTROS ── */
function iniciarFiltros() {
  // Filtro de categoria
  document.getElementById('catList').addEventListener('click', e => {
    const btn = e.target.closest('.cat-btn[data-cat]');
    if (!btn) return;
    document.querySelectorAll('.cat-btn[data-cat]').forEach(b => b.classList.remove('active'));
    btn.classList.add('active');
    catAtiva = btn.dataset.cat;
    filterAtivo = null;
    document.querySelectorAll('.cat-btn[data-filter]').forEach(b => b.classList.remove('active'));
    aplicarFiltros();
  });

  // Filtro de destaque e promoção
  document.querySelectorAll('.cat-btn[data-filter]').forEach(btn => {
    btn.addEventListener('click', () => {
      const f = btn.dataset.filter;
      if (filterAtivo === f) {
        filterAtivo = null; btn.classList.remove('active');
      } else {
        filterAtivo = f;
        document.querySelectorAll('.cat-btn[data-filter]').forEach(b => b.classList.remove('active'));
        btn.classList.add('active');
      }
      aplicarFiltros();
    });
  });

  // Filtro de gênero
  document.querySelectorAll('.cat-btn[data-genero]').forEach(btn => {
    btn.addEventListener('click', () => {
      document.querySelectorAll('.cat-btn[data-genero]').forEach(b => b.classList.remove('active'));
      btn.classList.add('active');
      generoAtivo = btn.dataset.genero;
      aplicarFiltros();
    });
  });
}

function iniciarBusca() {
  document.getElementById('searchInput').addEventListener('input', aplicarFiltros);
}

function aplicarFiltros() {
  const termo = document.getElementById('searchInput').value.toLowerCase().trim();
  let lista = todosOsProdutos;

  if (catAtiva !== 'todos') {
    lista = lista.filter(p => p.categoria === catAtiva);
  }
  if (filterAtivo === 'destaque') lista = lista.filter(p => p.destaque);
  if (filterAtivo === 'promocao')  lista = lista.filter(p => p.promocao);
  if (termo) {
    lista = lista.filter(p =>
      p.nome.toLowerCase().includes(termo) ||
      (p.descricao || '').toLowerCase().includes(termo)
    );
  }
  if (generoAtivo !== 'todos') {
    lista = lista.filter(p => p.genero === generoAtivo);
  }
  renderizar(lista);
}

function resetFiltros() {
  catAtiva = 'todos'; filterAtivo = null;
  document.querySelectorAll('.cat-btn').forEach(b => b.classList.remove('active'));
  document.querySelector('.cat-btn[data-cat="todos"]').classList.add('active');
  document.getElementById('searchInput').value = '';
  generoAtivo = 'todos';
  document.querySelectorAll('.cat-btn[data-genero]').forEach(b => b.classList.remove('active'));
  renderizar(todosOsProdutos);
}

/* ── WHATSAPP ── */
function abrirWhatsApp(id) {
  const p = todosOsProdutos.find(x => x.id === id);
  if (!p) return;
  const preco = 'R$ ' + parseFloat(p.preco).toFixed(2).replace('.', ',');
  const msg = `Olá! Vi o catálogo da *Sua Essência* e tenho interesse no produto:\n\n*${p.nome}* — ${preco}\n\nPoderia me dar mais informações?`;
  window.open(`https://wa.me/${WHATSAPP}?text=${encodeURIComponent(msg)}`, '_blank');
}
