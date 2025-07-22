const token = localStorage.getItem("token");

if (!token) {
  alert("Você precisa estar logado.");
  window.location.href = "login.html";
}

const payload = JSON.parse(atob(token.split('.')[1]));
const role = payload.role || "";
const isAdmin = role === "ROLE_ADMIN";

const form = document.getElementById("formCurso");
const tabela = document.getElementById("tabelaCursos");

async function carregarCursos() {
  try {
    const resposta = await fetch("http://localhost:8080/cursos", {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    if (!resposta.ok) throw new Error("Erro ao carregar cursos.");

    const cursos = await resposta.json();
    tabela.innerHTML = "";

    cursos.forEach(curso => {
      const linha = document.createElement("tr");

      linha.innerHTML = `
        <td>${curso.id}</td>
        <td>${curso.nome}</td>
        <td style="text-align: center;">
          ${isAdmin ? `
            <button onclick="excluirCurso(${curso.id})" title="Excluir curso" style="background: none; border: none; cursor: pointer;">
              🗑️
            </button>` : ""}
        </td>
      `;

      tabela.appendChild(linha);
    });
  } catch (erro) {
    console.error("Erro ao carregar cursos:", erro);
    tabela.innerHTML = "<tr><td colspan='3'>Não foi possível carregar os cursos.</td></tr>";
  }
}

form.addEventListener("submit", async (e) => {
  e.preventDefault();
  const nome = document.getElementById("nomeCurso").value;

  try {
    const resposta = await fetch("http://localhost:8080/cursos", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify({ nome })
    });

    if (!resposta.ok) throw new Error("Erro ao cadastrar curso.");

    form.reset();
    carregarCursos();
  } catch (erro) {
    console.error("Erro ao cadastrar curso:", erro);
  }
});

async function excluirCurso(id) {
  if (!confirm("Deseja realmente excluir este curso?")) return;

  try {
    const resposta = await fetch(`http://localhost:8080/cursos/${id}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    if (!resposta.ok) throw new Error("Erro ao excluir curso.");

    alert("Curso excluído com sucesso.");
    carregarCursos(); // Atualiza a lista

  } catch (erro) {
    console.error("Erro ao excluir curso:", erro);
    alert("Falha ao excluir curso.");
  }
}

carregarCursos();