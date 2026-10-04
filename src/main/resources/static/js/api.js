const API_URL = "/notificacao";

async function apiRequest(caminho = "", options = {}) {

    const config = {
        ...options,
        headers: {
            "Content-Type": "application/json",
            ...(options.headers || {})
        }
    };

    const response =
        await fetch(`${API_URL}${caminho}`, config);

    if (response.status === 204) {
        return null;
    }

    const contentType =
        response.headers.get("content-type") || "";

    let body;

    if (contentType.includes("json")) {
        body = await response.json();
    } else {
        body = await response.text();
    }

    if (!response.ok) {

        const mensagem =
            body?.detail
            || body?.title
            || "Não foi possível realizar a operação.";

        throw new Error(mensagem);
    }

    return body;
}

function criarNotificacao(notificacao) {

    return apiRequest("", {
        method: "POST",
        body: JSON.stringify(notificacao)
    });
}

function atualizarNotificacao(id, notificacao) {

    return apiRequest(`/${id}`, {
        method: "PUT",
        body: JSON.stringify(notificacao)
    });
}

function buscarNotificacao(id) {

    return apiRequest(`/${id}`, {
        method: "GET"
    });
}

function excluirNotificacao(id) {

    return apiRequest(`/${id}`, {
        method: "DELETE"
    });
}

function listarNotificacoes(filtros = {}) {

    const parametros = new URLSearchParams();

    if (filtros.agravo) {
        parametros.append(
            "agravo",
            filtros.agravo
        );
    }

    if (filtros.nomePaciente) {
        parametros.append(
            "nomePaciente",
            filtros.nomePaciente
        );
    }

    if (filtros.duplicadas) {
        parametros.append(
            "duplicadas",
            "true"
        );
    }

    const query =
        parametros.toString();

    const caminho =
        query ? `?${query}` : "";

    return apiRequest(caminho, {
        method: "GET"
    });
}