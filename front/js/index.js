let isAdmin = false;
let paginaAtual = 0;
let totalPaginas = 1;
const token = localStorage.getItem("token");

if (!token) {
  alert("Você precisa fazer login.");
  window.location.href = "login.html";
} else {
  const payload = JSON.parse(atob(token.split('.')[1]));
  const role = payload.role || "";
  isAdmin = role === "ROLE_ADMIN";

  carregarTopicos(paginaAtual);
}

async function carregarTopicos(pagina = 0) {
  try {
    const resposta = await fetch(`http://localhost:8080/topicos?page=${pagina}&size=10`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    if (!resposta.ok) throw new Error("Erro ao buscar tópicos.");

    const dados = await resposta.json();
    const topicos = dados.content;
    totalPaginas = dados.totalPages;
    paginaAtual = dados.number;

    const lista = document.getElementById("listaTopicos");
    lista.innerHTML = "";

    topicos.forEach(t => {
      const item = document.createElement("li");
      item.style.cursor = "pointer";

      item.onclick = (e) => {
        if (e.target.classList.contains("btn-excluir") || e.target.classList.contains("btn-fechar")) return;
        window.location.href = `topic.html?id=${t.id}`;
      };

      const botaoExcluir = isAdmin
        ? `<button class="btn-excluir" onclick="event.stopPropagation(); excluirTopico(${t.id})">🗑️ Excluir</button>`
        : "";

      const botaoFechar = isAdmin && t.status !== "FECHADO"
        ? `<button class="btn-fechar" onclick="event.stopPropagation(); fecharTopico(${t.id})">🚪 Fechar tópico</button>`
        : "";

      item.innerHTML = `
        <strong>${t.titulo}</strong><br />
        <small>
          Autor: ${t.autor?.nome || "Desconhecido"} (${t.autor?.email || "-"})<br />
          Curso: ${t.curso?.nome || "N/A"} | Status: <strong>${t.status || "N/A"}</strong><br />
          Criado em: ${new Date(t.dataInc).toLocaleString()}<br />
          ${botaoExcluir} ${botaoFechar}
        </small>
      `;

      lista.appendChild(item);
    });

    atualizarControles();

  } catch (erro) {
    console.error("Erro ao carregar tópicos:", erro);
    document.getElementById("listaTopicos").textContent = "Não foi possível carregar os tópicos.";
  }
}

function atualizarControles() {
  document.getElementById("paginaInfo").textContent = `Página ${paginaAtual + 1} de ${totalPaginas}`;
  document.getElementById("btnAnterior").disabled = paginaAtual === 0;
  document.getElementById("btnProxima").disabled = paginaAtual >= totalPaginas - 1;
}

function navegarPagina(direcao) {
  const novaPagina = paginaAtual + direcao;
  if (novaPagina >= 0 && novaPagina < totalPaginas) {
    carregarTopicos(novaPagina);
  }
}

async function excluirTopico(id) {
  const confirmar = confirm("Tem certeza que deseja excluir este tópico?");
  if (!confirmar) return;

  try {
    const resposta = await fetch(`http://localhost:8080/topicos/${id}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    if (!resposta.ok) throw new Error("Erro ao excluir tópico.");

    alert("Tópico excluído com sucesso.");
    carregarTopicos(paginaAtual);

  } catch (erro) {
    console.error("Erro ao excluir:", erro);
    alert("Falha ao excluir tópico.");
  }
}

async function fecharTopico(id) {
  const confirmar = confirm("Deseja realmente fechar este tópico?");
  if (!confirmar) return;

  try {
    const resposta = await fetch(`http://localhost:8080/topicos/${id}/status`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify({ status: "FECHADO" })
    });

    if (!resposta.ok) throw new Error("Erro ao fechar tópico.");

    alert("Tópico marcado como FECHADO.");
    carregarTopicos(paginaAtual);

  } catch (erro) {
    console.error("Erro ao fechar tópico:", erro);
    alert("Falha ao atualizar status.");
  }
}