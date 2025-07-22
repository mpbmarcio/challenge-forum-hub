const token = localStorage.getItem("token");
const autorId = localStorage.getItem("usuarioId");

if (!token) {
  alert("Você precisa estar logado.");
  window.location.href = "login.html";
}

const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get("id");

if (!id) {
  alert("ID do tópico não especificado.");
  window.location.href = "index.html";
}

async function carregarTopico(id) {
  try {
    const resposta = await fetch(`http://localhost:8080/topicos/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    if (!resposta.ok) throw new Error("Erro ao carregar tópico.");

    const topico = await resposta.json();

    document.getElementById("tituloTopico").textContent = topico.titulo;
    document.getElementById("mensagemTopico").textContent = topico.mensagem;
    document.getElementById("cursoTopico").textContent = topico.curso?.nome || "N/A";
    document.getElementById("statusTopico").textContent = topico.status || "N/A";
    document.getElementById("autorTopico").textContent = `${topico.autor?.nome || "Desconhecido"} (${topico.autor?.email || "-"})`;
    document.getElementById("dataTopico").textContent = new Date(topico.dataInc).toLocaleString();

    const lista = document.getElementById("listaRespostas");
    lista.innerHTML = "";

    if (topico.respostas && topico.respostas.length > 0) {
      topico.respostas.forEach(r => {
        console.log("Data recebida da resposta:", r.dataInc);
        const li = document.createElement("li");
        li.innerHTML = `
          <p>${r.mensagem}</p>
          <small>Por: ${r.autor?.nome || "Desconhecido"} - ${new Date(r.dataInc).toLocaleString()}</small>
        `;
        lista.appendChild(li);
      });
    } else {
      lista.innerHTML = "<li>Este tópico ainda não possui respostas.</li>";
    }

    if (topico.status === "FECHADO") {
      const form = document.getElementById("formResposta");
      if (form) form.style.display = "none";

      const aviso = document.createElement("p");
      aviso.textContent = "Este tópico está fechado e não aceita novas respostas.";
      aviso.style.fontStyle = "italic";
      aviso.style.color = "gray";
      document.getElementById("formContainer")?.appendChild(aviso);
    }


  } catch (erro) {
    console.error("Erro ao carregar os detalhes do tópico:", erro);
    document.body.innerHTML = "<p>Não foi possível carregar os dados do tópico.</p>";
  }
}

carregarTopico(id);

document.getElementById("formResposta").addEventListener("submit", async function (e) {
  e.preventDefault();

  const mensagem = document.getElementById("mensagemResposta").value;
  const autorId = localStorage.getItem("usuarioId");

  if (!autorId) {
    alert("Usuário não identificado. Faça login novamente.");
    window.location.href = "login.html";
    return;
  }

  const corpo = {
    mensagem,
    autorId: parseInt(autorId),
    topicoId: parseInt(id),
    dataInc: new Date().toISOString()
  };

  try {
    const resposta = await fetch("http://localhost:8080/respostas", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify(corpo)
    });

    console.log("Resposta enviada. Código:", resposta.status);


    if (!resposta.ok) throw new Error("Erro ao enviar resposta.");

    console.log("Agora atualizando status...");


    await fetch(`http://localhost:8080/topicos/${id}/status`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify({ status: "RESPONDIDO" })
    });

    alert("Resposta enviada com sucesso!");
    location.reload();

  } catch (erro) {
    console.error("Erro ao enviar resposta:", erro);
    document.getElementById("respostaErro").textContent = "Não foi possível enviar a resposta.";
  }
});