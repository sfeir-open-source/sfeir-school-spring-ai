import { x as p, U as c, _ as g, $ as f, n as u } from "./copilot-8Jex3Khy.js";
import { r as h } from "./state-BV-9Tqs8.js";
import { B as m } from "./base-panel-DXfnLZcK.js";
import { i as v } from "./icons-C2RMHmnu.js";
const b = "copilot-features-panel{padding:var(--space-100);font:var(--font-xsmall);display:grid;grid-template-columns:auto 1fr;gap:var(--space-50);height:auto}copilot-features-panel a{display:flex;align-items:center;gap:var(--space-50);white-space:nowrap}copilot-features-panel a svg{height:12px;width:12px;min-height:12px;min-width:12px}";
var F = Object.defineProperty, $ = Object.getOwnPropertyDescriptor, d = (e, t, a, o) => {
  for (var s = o > 1 ? void 0 : o ? $(t, a) : t, r = e.length - 1, l; r >= 0; r--)
    (l = e[r]) && (s = (o ? l(t, a, s) : l(s)) || s);
  return o && s && F(t, a, s), s;
};
const n = window.Vaadin.devTools;
let i = class extends m {
  constructor() {
    super(...arguments), this.features = [], this.handleFeatureFlags = (e) => {
      this.features = e.data.features;
    };
  }
  connectedCallback() {
    super.connectedCallback(), this.onCommand("featureFlags", this.handleFeatureFlags);
  }
  render() {
    return p` <style>
        ${b}
      </style>
      ${this.features.map(
      (e) => p`
          <copilot-toggle-button
            .title="${e.title}"
            ?checked=${e.enabled}
            @on-change=${(t) => this.toggleFeatureFlag(t, e)}>
          </copilot-toggle-button>
          <a class="ahreflike" href="${e.moreInfoLink}" title="Learn more" target="_blank"
            >learn more ${v.linkExternal}</a
          >
        `
    )}`;
  }
  toggleFeatureFlag(e, t) {
    const a = e.target.checked;
    c("use-feature", { source: "toggle", enabled: a, id: t.id }), n.frontendConnection ? (n.frontendConnection.send("setFeature", { featureId: t.id, enabled: a }), g({
      type: f.INFORMATION,
      message: `“${t.title}” ${a ? "enabled" : "disabled"}`,
      details: t.requiresServerRestart ? "This feature requires a server restart" : void 0,
      dismissId: `feature${t.id}${a ? "Enabled" : "Disabled"}`
    })) : n.log("error", `Unable to toggle feature ${t.title}: No server connection available`);
  }
};
d([
  h()
], i.prototype, "features", 2);
i = d([
  u("copilot-features-panel")
], i);
const w = {
  header: "Features",
  expanded: !1,
  panelOrder: 35,
  panel: "right",
  floating: !1,
  tag: "copilot-features-panel",
  helpUrl: "https://vaadin.com/docs/latest/flow/configuration/feature-flags"
}, x = {
  init(e) {
    e.addPanel(w);
  }
};
window.Vaadin.copilot.plugins.push(x);
export {
  i as CopilotFeaturesPanel
};
