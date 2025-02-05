import { n as u, x as d, R as g, K as l, y as h } from "./copilot-8Jex3Khy.js";
import { B as f } from "./base-panel-DXfnLZcK.js";
import { i as e } from "./icons-C2RMHmnu.js";
const m = "copilot-shortcuts-panel{font:var(--font-xsmall);padding:var(--space-200);display:flex;flex-direction:column;gap:var(--space-50)}copilot-shortcuts-panel h3{font:var(--font-xsmall-strong);margin:0;padding:0}copilot-shortcuts-panel h3:not(:first-of-type){margin-top:var(--space-200)}copilot-shortcuts-panel ul{list-style:none;margin:0;padding:0 var(--space-50);display:flex;flex-direction:column}copilot-shortcuts-panel ul li{display:flex;align-items:center;gap:var(--space-150);padding:var(--space-75) 0}copilot-shortcuts-panel ul li:not(:last-of-type){border-bottom:1px dashed var(--border-color)}copilot-shortcuts-panel ul li svg{height:16px;width:16px}copilot-shortcuts-panel ul li .kbds{flex:1;text-align:right}copilot-shortcuts-panel kbd{display:inline-block;border-radius:var(--radius-1);border:1px solid var(--border-color);min-width:1em;min-height:1em;text-align:center;margin:0 .1em;padding:.25em;box-sizing:border-box;font-size:var(--font-size-1);font-family:var(--font-family);line-height:1}";
var $ = Object.defineProperty, b = Object.getOwnPropertyDescriptor, v = (i, s, n, a) => {
  for (var o = a > 1 ? void 0 : a ? b(s, n) : s, r = i.length - 1, p; r >= 0; r--)
    (p = i[r]) && (o = (a ? p(s, n, o) : p(o)) || o);
  return a && o && $(s, n, o), o;
};
let c = class extends f {
  render() {
    return d`<style>
        ${m}
      </style>
      <h3>Global</h3>
      <ul>
        <li>${e.vaadinLogo} Copilot ${t(l.toggleCopilot)}</li>
        <li>${e.terminal} Command window ${t(l.toggleCommandWindow)}</li>
        <li>${e.undo} Undo ${t(l.undo)}</li>
        <li>${e.redo} Redo ${t(l.redo)}</li>
      </ul>
      <h3>Selected component</h3>
      <ul>
        <li>${e.code} Go to source ${t(l.goToSource)}</li>
        <li>${e.copy} Copy ${t(l.copy)}</li>
        <li>${e.paste} Paste ${t(l.paste)}</li>
        <li>${e.duplicate} Duplicate ${t(l.duplicate)}</li>
        <li>${e.userUp} Select parent ${t(l.selectParent)}</li>
        <li>${e.userLeft} Select previous sibling ${t(l.selectPreviousSibling)}</li>
        <li>${e.userRight} Select first child / next sibling ${t(l.selectNextSibling)}</li>
        <li>${e.trash} Delete ${t(l.delete)}</li>
      </ul>`;
  }
};
c = v([
  u("copilot-shortcuts-panel")
], c);
function t(i) {
  return d`<span class="kbds">${g(i)}</span>`;
}
const x = h({
  header: "Keyboard Shortcuts",
  tag: "copilot-shortcuts-panel",
  width: 400,
  height: 550,
  floatingPosition: {
    top: 50,
    left: 50
  }
}), y = {
  init(i) {
    i.addPanel(x);
  }
};
window.Vaadin.copilot.plugins.push(y);
