import { useEffect, useMemo, useState } from 'react'
import {
  ArrowRight, Award, BookOpen, Check, ChevronRight, Coins, Gift, LogOut,
  Mail, Menu, Plus, Send, ShieldCheck, Sparkles, UserRound, X,
} from 'lucide-react'
import { api } from './api'

const money = (value) => `${Number(value || 0).toLocaleString('pt-BR')} moedas`
const emptyAddress = { logradouro: '', numero: '', complemento: '', bairro: '', cidade: '', estado: '', cep: '' }

function Field({ label, ...props }) {
  return <label className="block"><span className="label">{label}</span><input className="field" {...props} /></label>
}

function Select({ label, children, ...props }) {
  return <label className="block"><span className="label">{label}</span><select className="field" {...props}>{children}</select></label>
}

function Modal({ title, onClose, children }) {
  return <div className="fixed inset-0 z-30 flex items-center justify-center bg-ink/40 p-4 backdrop-blur-sm">
    <div className="max-h-[92vh] w-full max-w-xl overflow-y-auto rounded-3xl bg-white p-6 shadow-2xl">
      <div className="mb-5 flex items-center justify-between"><h2 className="text-xl font-black text-ink">{title}</h2><button onClick={onClose} className="rounded-full p-2 text-slate-400 hover:bg-slate-100"><X size={20} /></button></div>
      {children}
    </div>
  </div>
}

function Auth({ onLogin }) {
  const [mode, setMode] = useState('login')
  const [role, setRole] = useState('ALUNO')
  const [institutions, setInstitutions] = useState([])
  const [companies, setCompanies] = useState([])
  const [form, setForm] = useState({ nome: '', email: '', login: '', senha: '', cpf: '', rg: '', curso: '', departamento: '', cnpj: '', associacaoId: '', ...emptyAddress })
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const update = (key) => (event) => setForm({ ...form, [key]: event.target.value })

  useEffect(() => {
    api.institutions().then(setInstitutions).catch(() => {})
    api.companies().then(setCompanies).catch(() => {})
  }, [])

  const submit = async (event) => {
    event.preventDefault(); setError(''); setLoading(true)
    try {
      if (mode === 'login') onLogin(await api.login({ login: form.login, senha: form.senha }))
      else {
        const address = { logradouro: form.logradouro, numero: form.numero, complemento: form.complemento, bairro: form.bairro, cidade: form.cidade, estado: form.estado, cep: form.cep }
        const body = role === 'ALUNO'
          ? {
            nome: form.nome,
            email: form.email,
            login: form.login,
            senha: form.senha,
            cpf: form.cpf,
            rg: form.rg,
            curso: form.curso,
            instituicaoId: form.associacaoId.startsWith('instituicao:')
              ? Number(form.associacaoId.split(':')[1])
              : null,
            empresaParceiraId: form.associacaoId.startsWith('empresa:')
              ? Number(form.associacaoId.split(':')[1])
              : null,
            endereco: address,
          }
          : { nome: form.nome, email: form.email, login: form.login, senha: form.senha, cnpj: form.cnpj, endereco: address }
        if (role === 'ALUNO') await api.createStudent(body)
        if (role === 'EMPRESA') await api.createCompany(body)
        onLogin(await api.login({ login: form.login, senha: form.senha }))
      }
    } catch (e) { setError(e.message) } finally { setLoading(false) }
  }

  return <main className="min-h-screen overflow-hidden bg-[#f7fbf9]">
    <div className="mx-auto grid min-h-screen max-w-7xl lg:grid-cols-[1.05fr_.95fr]">
      <section className="relative hidden overflow-hidden bg-teal p-12 text-white lg:flex lg:flex-col lg:justify-between">
        <div className="absolute -right-32 -top-32 h-96 w-96 rounded-full bg-white/10" /><div className="absolute -bottom-40 -left-20 h-96 w-96 rounded-full bg-emerald-950/20" />
        <div className="relative"><div className="mb-16 flex items-center gap-2 text-xl font-black"><Coins size={27} /> Moeda+</div><p className="max-w-lg text-6xl font-black leading-[.98] tracking-tight">Reconhecimento que circula.</p><p className="mt-6 max-w-md text-lg leading-8 text-teal-50">Uma forma simples de valorizar pessoas, compartilhar conquistas e transformar mérito em oportunidades.</p></div>
        <div className="relative grid grid-cols-3 gap-3 text-sm text-teal-50"><div><Sparkles className="mb-2" size={18} /><b className="block text-white">Mérito</b>celebrado</div><div><ShieldCheck className="mb-2" size={18} /><b className="block text-white">Transparente</b>e seguro</div><div><Gift className="mb-2" size={18} /><b className="block text-white">Vantagens</b>para todos</div></div>
      </section>
      <section className="flex items-center justify-center p-5 sm:p-10"><div className="w-full max-w-md">
        <div className="mb-8 flex items-center gap-2 text-xl font-black text-teal lg:hidden"><Coins size={25} /> Moeda+</div>
        <div className="mb-8"><p className="mb-2 text-sm font-bold uppercase tracking-[.18em] text-teal">Bem-vindo</p><h1 className="text-4xl font-black tracking-tight text-ink">{mode === 'login' ? 'Entre na sua conta' : 'Crie seu acesso'}</h1><p className="mt-3 text-slate-500">{mode === 'login' ? 'Continue espalhando reconhecimento pela comunidade.' : 'Escolha seu perfil e comece agora.'}</p></div>
        <div className="mb-6 flex rounded-2xl bg-slate-100 p-1"><button onClick={() => setMode('login')} className={`flex-1 rounded-xl py-2.5 text-sm font-bold ${mode === 'login' ? 'bg-white text-ink shadow-sm' : 'text-slate-500'}`}>Entrar</button><button onClick={() => setMode('register')} className={`flex-1 rounded-xl py-2.5 text-sm font-bold ${mode === 'register' ? 'bg-white text-ink shadow-sm' : 'text-slate-500'}`}>Criar conta</button></div>
        {mode === 'register' && <div className="mb-5 grid grid-cols-2 gap-2">{[['ALUNO', 'Aluno'], ['EMPRESA', 'Empresa']].map(([value, label]) => <button key={value} onClick={() => setRole(value)} className={`rounded-xl border px-2 py-2.5 text-xs font-bold ${role === value ? 'border-teal bg-mint text-teal' : 'border-slate-200 text-slate-500'}`}>{label}</button>)}</div>}
        <form onSubmit={submit} className="space-y-4">
          {mode === 'register' && <><Field label="Nome" value={form.nome} onChange={update('nome')} required /><Field label="E-mail" type="email" value={form.email} onChange={update('email')} required /></>}
          <div className="grid gap-4 sm:grid-cols-2"><Field label="Login" value={form.login} onChange={update('login')} required /><Field label="Senha" type="password" value={form.senha} onChange={update('senha')} required /></div>
          {mode === 'register' && role === 'ALUNO' && <div className="grid gap-4 sm:grid-cols-2"><Field label="CPF" value={form.cpf} onChange={update('cpf')} required /><Field label="RG" value={form.rg} onChange={update('rg')} required /></div>}
          {mode === 'register' && role === 'ALUNO' && <div className="grid gap-4 sm:grid-cols-2"><Field label="Curso" value={form.curso} onChange={update('curso')} required /><Select label="Instituição" value={form.associacaoId} onChange={update('associacaoId')} required><option value="">Selecione</option><optgroup label="Instituições acadêmicas">{institutions.map((item) => <option key={`instituicao-${item.id}`} value={`instituicao:${item.id}`}>{item.nome}</option>)}</optgroup><optgroup label="Empresas parceiras">{companies.map((item) => <option key={`empresa-${item.id}`} value={`empresa:${item.id}`}>{item.nome}</option>)}</optgroup></Select></div>}
          {mode === 'register' && role === 'ALUNO' && <div className="space-y-4 rounded-2xl bg-slate-50 p-4"><p className="text-sm font-bold text-ink">Endereço</p><div className="grid gap-4 sm:grid-cols-[1fr_120px]"><Field label="Logradouro" value={form.logradouro} onChange={update('logradouro')} required /><Field label="Número" value={form.numero} onChange={update('numero')} required /></div><Field label="Complemento" value={form.complemento} onChange={update('complemento')} /><div className="grid gap-4 sm:grid-cols-2"><Field label="Bairro" value={form.bairro} onChange={update('bairro')} required /><Field label="Cidade" value={form.cidade} onChange={update('cidade')} required /></div><div className="grid gap-4 sm:grid-cols-2"><Field label="Estado" value={form.estado} onChange={update('estado')} required /><Field label="CEP" value={form.cep} onChange={update('cep')} required /></div></div>}
          {mode === 'register' && role === 'EMPRESA' && <Field label="CNPJ" value={form.cnpj} onChange={update('cnpj')} required />}
          {error && <p className="rounded-xl bg-red-50 px-3 py-2 text-sm font-semibold text-red-600">{error}</p>}
          <button className="primary flex w-full items-center justify-center gap-2 py-3" disabled={loading}>{loading ? 'Aguarde...' : mode === 'login' ? 'Entrar na plataforma' : 'Criar minha conta'}<ArrowRight size={17} /></button>
        </form>
      </div></section>
    </div>
  </main>
}

function Stat({ icon: Icon, label, value, accent = false }) {
  return <div className={`card ${accent ? 'bg-teal text-white' : ''}`}><div className={`mb-5 flex h-10 w-10 items-center justify-center rounded-xl ${accent ? 'bg-white/15' : 'bg-mint text-teal'}`}><Icon size={20} /></div><p className={`text-sm ${accent ? 'text-teal-50' : 'text-slate-500'}`}>{label}</p><p className="mt-1 text-2xl font-black">{value}</p></div>
}

function Statement({ data, loading }) {
  return <div className="card"><div className="mb-5 flex items-center justify-between"><div><p className="text-xs font-bold uppercase tracking-widest text-teal">Atividade</p><h2 className="mt-1 text-xl font-black">Seu extrato</h2></div><BookOpen className="text-slate-300" /></div>{loading ? <p className="text-sm text-slate-400">Carregando extrato...</p> : !data?.transacoes?.length ? <p className="rounded-2xl bg-slate-50 p-5 text-sm text-slate-500">Nenhuma transação registrada ainda.</p> : <div className="space-y-3">{data.transacoes.map((item) => <div key={item.id} className="flex items-center gap-3 rounded-2xl bg-slate-50 p-3"><div className="flex h-10 w-10 shrink-0 items-center justify-center rounded-xl bg-mint text-teal">{item.tipo === 'RESGATE' ? <Gift size={18} /> : <Send size={18} />}</div><div className="min-w-0 flex-1"><p className="truncate text-sm font-bold">{item.descricao || item.tipo}</p><p className="text-xs text-slate-400">{new Date(item.dataHora).toLocaleString('pt-BR')}</p></div><div className="text-right"><p className="text-sm font-black text-teal">{money(item.valor)}</p>{item.codigoCupom && <p className="text-[11px] text-slate-500">Cupom: {item.codigoCupom}</p>}</div></div>)}</div>}</div>
}

function Dashboard({ user, onLogout, onUserUpdate }) {
  const [advantages, setAdvantages] = useState([])
  const [people, setPeople] = useState([])
  const [statement, setStatement] = useState(null)
  const [loadingStatement, setLoadingStatement] = useState(true)
  const [modal, setModal] = useState(null)
  const [notice, setNotice] = useState('')
  const [error, setError] = useState('')
  const [menu, setMenu] = useState(false)
  const [refresh, setRefresh] = useState(0)

  const reload = () => setRefresh((value) => value + 1)
  useEffect(() => {
    if (user.perfil === 'ALUNO') {
      api.advantages(true).then(setAdvantages).catch((e) => setError(e.message))
      setLoadingStatement(true); api.studentStatement(user.id).then(setStatement).catch((e) => setError(e.message)).finally(() => setLoadingStatement(false))
    }
    if (user.perfil === 'PROFESSOR' || user.perfil === 'EMPRESA') {
      if (user.perfil === 'EMPRESA') {
        api.students()
          .then((items) => setPeople(items.filter((person) => person.empresaParceira?.id === user.id)))
          .catch((e) => setError(e.message))
      } else {
        Promise.all([api.professors(), api.students()])
          .then(([professors, students]) => {
            const professor = professors.find((person) => person.id === user.id)
            const institutionId = professor?.instituicao?.id
            setPeople(institutionId
              ? students.filter((person) => person.instituicao?.id === institutionId)
              : [])
          })
          .catch((e) => setError(e.message))
      }
    }
    if (user.perfil === 'PROFESSOR') {
      setLoadingStatement(true); api.professorStatement(user.id).then(setStatement).catch((e) => setError(e.message)).finally(() => setLoadingStatement(false))
    }
    if (user.perfil === 'EMPRESA') {
      api.advantages().then((items) => setAdvantages(items.filter((item) => item.empresaParceira?.id === user.id))).catch((e) => setError(e.message))
      setLoadingStatement(true); api.companyStatement(user.id).then(setStatement).catch((e) => setError(e.message)).finally(() => setLoadingStatement(false))
    }
  }, [user.id, user.perfil, refresh])

  const title = user.perfil === 'ALUNO' ? 'Seu espaço de conquistas' : user.perfil === 'PROFESSOR' ? 'Reconheça quem faz a diferença' : 'Reconheça e ofereça benefícios'
  const action = async (fn, success) => {
    setError('')
    try {
      await fn()
      if (user.perfil === 'ALUNO') {
        const updated = (await api.students()).find((person) => person.id === user.id)
        if (updated) setUserBalance(updated.saldo)
      }
      if (user.perfil === 'PROFESSOR') {
        const updated = (await api.professors()).find((person) => person.id === user.id)
        if (updated) setUserBalance(updated.saldo)
      }
      setNotice(success); setModal(null); reload(); setTimeout(() => setNotice(''), 4000)
    } catch (e) { setError(e.message) }
  }
  const setUserBalance = (saldo) => {
    const updatedUser = { ...user, saldo }
    localStorage.setItem('moeda-user', JSON.stringify(updatedUser))
    onUserUpdate(updatedUser)
  }

  return <div className="min-h-screen bg-[#f7fbf9]"><header className="sticky top-0 z-20 border-b border-slate-100 bg-white/90 backdrop-blur"><div className="mx-auto flex max-w-7xl items-center justify-between px-5 py-4 sm:px-8"><div className="flex items-center gap-2 text-xl font-black text-teal"><Coins size={25} /> Moeda+</div><div className="hidden items-center gap-4 sm:flex"><div className="text-right"><p className="text-sm font-bold">{user.nome}</p><p className="text-xs font-semibold uppercase tracking-wide text-teal">{user.perfil.toLowerCase()}</p></div><button onClick={onLogout} className="rounded-xl p-2 text-slate-400 hover:bg-slate-100 hover:text-red-500"><LogOut size={19} /></button></div><button onClick={() => setMenu(!menu)} className="rounded-xl p-2 text-slate-500 sm:hidden"><Menu size={21} /></button></div>{menu && <div className="border-t border-slate-100 p-3 sm:hidden"><button onClick={onLogout} className="flex w-full items-center gap-2 rounded-xl p-2 text-sm font-bold text-red-500"><LogOut size={17} /> Sair</button></div>}</header>
    <main className="mx-auto max-w-7xl px-5 py-8 sm:px-8"><div className="mb-8 flex flex-col justify-between gap-5 md:flex-row md:items-end"><div><p className="mb-2 text-sm font-bold uppercase tracking-[.18em] text-teal">Olá, {user.nome.split(' ')[0]}</p><h1 className="text-3xl font-black tracking-tight text-ink sm:text-4xl">{title}</h1><p className="mt-2 text-slate-500">Tudo que você precisa para fazer a moeda circular.</p></div></div>
      {notice && <div className="mb-5 flex items-center gap-2 rounded-2xl bg-emerald-50 p-3 text-sm font-bold text-emerald-700"><Check size={18} />{notice}</div>}{error && <div className="mb-5 rounded-2xl bg-red-50 p-3 text-sm font-bold text-red-600">{error}</div>}
      <div className="mb-8 grid gap-4 sm:grid-cols-2 lg:grid-cols-3"><Stat icon={Coins} label="Saldo disponível" value={user.perfil === 'EMPRESA' ? 'Ilimitado' : money(user.saldo)} accent /><Stat icon={Award} label="Seu perfil" value={user.perfil === 'EMPRESA' ? 'Parceiro' : user.perfil === 'ALUNO' ? 'Estudante' : 'Professor'} /><Stat icon={ShieldCheck} label="Status da conta" value="Ativa" /></div>
      {user.perfil === 'ALUNO' && <StudentArea advantages={advantages} onRedeem={(advantage) => setModal({ type: 'redeem', advantage })} />}
      {user.perfil === 'PROFESSOR' && <ProfessorArea people={people} onTransfer={() => setModal({ type: 'transfer' })} />}
      {user.perfil === 'EMPRESA' && <CompanyArea advantages={advantages} onCreate={() => setModal({ type: 'advantage' })} onTransfer={() => setModal({ type: 'transfer' })} />}
      {user.perfil !== 'ALUNO' && <div className="mt-6"><Statement data={statement} loading={loadingStatement} /></div>}
    </main>
    {modal?.type === 'redeem' && <RedeemModal advantage={modal.advantage} user={user} onClose={() => setModal(null)} onSubmit={(body) => action(() => api.redeem(body), 'Resgate realizado! Seu cupom foi gerado.')} />}
    {modal?.type === 'transfer' && <TransferModal people={people} user={user} onClose={() => setModal(null)} onSubmit={(body) => action(() => api.transfer(body), 'Moedas enviadas com sucesso.')} />}
    {modal?.type === 'advantage' && <AdvantageModal user={user} onClose={() => setModal(null)} onSubmit={(body) => action(() => api.createAdvantage(body), 'Vantagem cadastrada com sucesso.')} />}
  </div>
}

function StudentArea({ advantages, onRedeem }) {
  return <section><div className="mb-4 flex items-end justify-between"><div><p className="text-xs font-bold uppercase tracking-widest text-teal">Para você</p><h2 className="mt-1 text-2xl font-black">Vantagens disponíveis</h2></div><span className="text-sm text-slate-400">{advantages.length} opções</span></div><div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">{advantages.map((item) => <div className="card group flex flex-col" key={item.id}><div className="mb-4 flex h-32 items-center justify-center overflow-hidden rounded-2xl bg-gradient-to-br from-mint to-teal/10">{item.foto ? <img src={item.foto} alt="" className="h-full w-full object-cover" /> : <Gift size={42} className="text-teal/40" />}</div><h3 className="text-lg font-black">{item.nome}</h3><p className="mt-1 line-clamp-2 min-h-10 text-sm text-slate-500">{item.descricao}</p><div className="mt-5 flex items-center justify-between"><span className="font-black text-teal">{money(item.custoMoedas)}</span><button className="secondary flex items-center gap-1 px-3 py-2 text-xs" onClick={() => onRedeem(item)}>Resgatar <ChevronRight size={14} /></button></div></div>)}</div></section>
}

function ProfessorArea({ people, onTransfer }) {
  return <section className="card"><div className="flex flex-col justify-between gap-4 sm:flex-row sm:items-center"><div><p className="text-xs font-bold uppercase tracking-widest text-teal">Reconhecimento</p><h2 className="mt-1 text-2xl font-black">Envie moedas para um aluno</h2><p className="mt-1 text-sm text-slate-500">Valorize uma boa atitude com uma mensagem.</p></div><button className="primary flex items-center justify-center gap-2" onClick={onTransfer}><Send size={17} /> Nova transferência</button></div></section>
}

function CompanyArea({ advantages, onCreate, onTransfer }) {
  return <section><div className="mb-4 flex flex-col justify-between gap-4 sm:flex-row sm:items-end"><div><p className="text-xs font-bold uppercase tracking-widest text-teal">Catálogo parceiro</p><h2 className="mt-1 text-2xl font-black">Suas vantagens personalizadas</h2></div><div className="flex gap-2"><button className="secondary flex items-center gap-2" onClick={onTransfer}><Send size={17} /> Enviar moedas</button><button className="primary flex items-center gap-2" onClick={onCreate}><Plus size={17} /> Cadastrar</button></div></div><div className="grid gap-3 md:grid-cols-2">{advantages.map((item) => <div className="card flex items-center gap-4" key={item.id}><div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-mint text-teal"><Gift size={21} /></div><div className="flex-1"><h3 className="font-black">{item.nome}</h3><p className="text-sm text-slate-500">{item.descricao}</p></div><span className="font-black text-teal">{money(item.custoMoedas)}</span></div>)}</div></section>
}

function RedeemModal({ advantage, user, onClose, onSubmit }) {
  return <Modal title={`Resgatar ${advantage.nome}`} onClose={onClose}><p className="mb-5 text-sm text-slate-500">O custo de <b>{money(advantage.custoMoedas)}</b> será debitado do seu saldo e um cupom único será gerado.</p><button className="primary w-full" onClick={() => onSubmit({ alunoId: user.id, vantagemId: advantage.id })}>Confirmar resgate</button></Modal>
}

function TransferModal({ people, user, onClose, onSubmit }) {
  const [form, setForm] = useState({ alunoId: '', valor: '', motivo: '' }); const update = (key) => (e) => setForm({ ...form, [key]: e.target.value })
  return <Modal title="Nova transferência" onClose={onClose}><div className="space-y-4"><Select label="Aluno destinatário" value={form.alunoId} onChange={update('alunoId')}><option value="">Selecione um aluno</option>{people.map((person) => <option key={person.id} value={person.id}>{person.nome} — saldo {person.saldo}</option>)}</Select><Field label="Quantidade de moedas" type="number" min="1" max={user.perfil === 'EMPRESA' ? undefined : user.saldo} value={form.valor} onChange={update('valor')} required /><label className="block"><span className="label">Motivo</span><textarea className="field min-h-24 resize-none" value={form.motivo} onChange={update('motivo')} required /></label><button className="primary w-full" onClick={() => onSubmit({ ...(user.perfil === 'EMPRESA' ? { empresaParceiraId: user.id } : { professorId: user.id }), alunoId: Number(form.alunoId), valor: Number(form.valor), motivo: form.motivo })}>Enviar moedas</button></div></Modal>
}

function AdvantageModal({ user, onClose, onSubmit }) {
  const [form, setForm] = useState({ nome: '', descricao: '', foto: '', custoMoedas: '' }); const update = (key) => (e) => setForm({ ...form, [key]: e.target.value })
  return <Modal title="Cadastrar vantagem" onClose={onClose}><div className="space-y-4"><Field label="Nome da vantagem" value={form.nome} onChange={update('nome')} required /><label className="block"><span className="label">Descrição</span><textarea className="field min-h-24 resize-none" value={form.descricao} onChange={update('descricao')} required /></label><Field label="URL da foto" value={form.foto} onChange={update('foto')} required /><Field label="Custo em moedas" type="number" min="1" value={form.custoMoedas} onChange={update('custoMoedas')} required /><button className="primary w-full" onClick={() => onSubmit({ ...form, custoMoedas: Number(form.custoMoedas), empresaParceiraId: user.id, ativa: true })}>Publicar vantagem</button></div></Modal>
}

export default function App() {
  const [user, setUser] = useState(() => { try { return JSON.parse(localStorage.getItem('moeda-user')) } catch { return null } })
  const login = (value) => { localStorage.setItem('moeda-user', JSON.stringify(value)); setUser(value) }
  const logout = () => { localStorage.removeItem('moeda-user'); setUser(null) }
  return user ? <Dashboard user={user} onLogout={logout} onUserUpdate={setUser} /> : <Auth onLogin={login} />
}
