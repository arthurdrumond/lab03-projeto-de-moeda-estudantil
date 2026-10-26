const statusMessages = {
  400: 'Confira os dados informados e tente novamente.',
  401: 'Login ou senha inválidos. Confira seus dados e tente novamente.',
  403: 'Você não tem permissão para realizar esta ação.',
  404: 'O recurso solicitado não foi encontrado.',
  409: 'Já existe um cadastro com esses dados.',
  500: 'O servidor encontrou um problema ao processar a solicitação. Tente novamente em instantes.',
  502: 'O servidor está temporariamente indisponível. Tente novamente em instantes.',
  503: 'O serviço está temporariamente indisponível. Tente novamente em instantes.',
}

const responseMessage = (status, body) => {
  const parsed = body ? (() => {
    try { return JSON.parse(body) } catch { return null }
  })() : null
  const detail = parsed?.detail || parsed?.message || parsed?.error || (typeof body === 'string' ? body : '')
  const technicalMessages = ['Bad Request', 'Unauthorized', 'Forbidden', 'Not Found', 'Internal Server Error', 'Service Unavailable']

  if (detail && !technicalMessages.includes(detail.trim())) return detail
  return statusMessages[status] || 'Não foi possível concluir a operação.'
}

const request = async (path, options = {}) => {
  let response
  try {
    response = await fetch(`/api${path}`, {
      headers: { 'Content-Type': 'application/json', ...(options.headers || {}) },
      ...options,
    })
  } catch {
    throw new Error('Não foi possível conectar ao servidor. Verifique se o backend está em execução e tente novamente.')
  }

  if (!response.ok) {
    const body = await response.text()
    throw new Error(responseMessage(response.status, body))
  }

  if (response.status === 204) return null
  return response.json()
}

export const api = {
  login: (body) => request('/auth/login', { method: 'POST', body: JSON.stringify(body) }),
  institutions: () => request('/instituicoes'),
  companies: () => request('/empresas-parceiras'),
  students: () => request('/alunos'),
  professors: () => request('/professores'),
  advantages: (active = false) => request(active ? '/vantagens/ativas' : '/vantagens'),
  createStudent: (body) => request('/alunos', { method: 'POST', body: JSON.stringify(body) }),
  createCompany: (body) => request('/empresas-parceiras', { method: 'POST', body: JSON.stringify(body) }),
  createAdvantage: (body) => request('/vantagens', { method: 'POST', body: JSON.stringify(body) }),
  transfer: (body) => request('/moedas/transferir', { method: 'POST', body: JSON.stringify(body) }),
  redeem: (body) => request('/moedas/resgatar', { method: 'POST', body: JSON.stringify(body) }),
  distribute: () => request('/moedas/distribuir-semestral', { method: 'POST' }),
  studentStatement: (id) => request(`/alunos/${id}/extrato`),
  professorStatement: (id) => request(`/professores/${id}/extrato`),
  companyStatement: (id) => request(`/empresas-parceiras/${id}/extrato`),
}
