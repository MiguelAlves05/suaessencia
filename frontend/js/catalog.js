/* =============================================
   CATALOG.JS — Lógica do Catálogo Público
   ============================================= */

const WHATSAPP = '5516999999999'; // ← SUBSTITUA pelo número real

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
  grid.innerHTML = `<div class="loading-pulse"></div>`.repeat(6);

  try {
    todosOsProdutos = await ProdutosAPI.listar();
  } catch {
    // Dados de exemplo enquanto o backend não está no ar
    todosOsProdutos = [
      { id:1, nome:'Oud Al Layl', categoria:'Árabe', preco:189.90, precoAnt:null, descricao:'Notas de oud, âmbar e sândalo envelhecido. Uma fragrância intensa e duradoura que evoca o exotismo do Oriente.', imagem:'https://images.unsplash.com/photo-1541643600914-78b084683702?w=500&q=80', destaque:true, promocao:false },
      { id:2, nome:'Rose Mystique', categoria:'Importados', preco:245.00, precoAnt:290.00, descricao:'Rosas búlgaras, jasmim e musk branco. Refinado e elegante, perfeito para ocasiões especiais.', imagem:'https://images.unsplash.com/photo-1592945403244-b3fbafd7f539?w=500&q=80', destaque:true, promocao:true },
      { id:3, nome:'Velvet Noir', categoria:'Importados', preco:320.00, precoAnt:null, descricao:'Bergamota italiana, madeira de cedro e baunilha de Madagascar. Sofisticação em cada gota.', imagem:'https://images.unsplash.com/photo-1556228578-8c89e6adf883?w=500&q=80', destaque:false, promocao:false },
      { id:4, nome:'Splash Floral', categoria:'Body Splash', preco:49.90, precoAnt:65.00, descricao:'Leveza floral com notas de peônia e framboesa. Refrescante para o dia a dia.', imagem:'https://images.unsplash.com/photo-1587017539504-67cfbddac569?w=500&q=80', destaque:false, promocao:true },
      { id:5, nome:'Ambar Royal', categoria:'Árabe', preco:210.00, precoAnt:null, descricao:'Âmbar dourado, almíscar e especiarias orientais. Envolvente e sensual.', imagem:'https://images.unsplash.com/photo-1523293182086-7651a899d37f?w=500&q=80', destaque:true, promocao:false },
      { id:6, nome:'Mini Chic', categoria:'Miniaturas', preco:35.90, precoAnt:null, descricao:'Miniatura de 10ml ideal para viagens. Elegância no tamanho certo.', imagem:'https://images.unsplash.com/photo-1445205170230-053b83016050?w=500&q=80', destaque:false, promocao:false },
      { id:7, nome:'Velvet Lip', categoria:'Maquiagem', preco:59.90, precoAnt:79.90, descricao:'Batom de longa duração com acabamento aveludado. Cor intensa e hidratação superior.', imagem:'https://images.unsplash.com/photo-1586495777744-4e6232bf4fa0?w=500&q=80', destaque:false, promocao:true },
      { id:8, nome:'Golden Oud', categoria:'Árabe', preco:275.00, precoAnt:null, descricao:'Oud premium com ouro coloidal, rosa turca e notas de baunilha.', imagem:'https://images.unsplash.com/photo-1541643600914-78b084683702?w=500&q=80', destaque:true, promocao:false },
    ];
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
