import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import Chat from './Chat/Chat.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Chat />
  </StrictMode>,
)
