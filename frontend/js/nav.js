/* nav.js */
document.addEventListener('DOMContentLoaded', () => {
  const toggle = document.getElementById('navToggle');
  const links  = document.getElementById('navLinks');
  if (toggle) toggle.addEventListener('click', () => links.classList.toggle('open'));
});
