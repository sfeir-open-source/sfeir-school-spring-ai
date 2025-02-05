import { j as M, a1 as T, a2 as w, Y as p, x as l, $ as c, a3 as S, U as L, M as D, b as R, a4 as C, n as y } from "./copilot-8Jex3Khy.js";
import { r as $ } from "./state-BV-9Tqs8.js";
import { B as I } from "./base-panel-DXfnLZcK.js";
import { i as n } from "./icons-C2RMHmnu.js";
const q = "copilot-log-panel{padding:var(--space-100);font:var(--font-xsmall);display:flex;flex-direction:column;gap:var(--space-50);overflow-y:auto;max-width:100vw}copilot-log-panel .row{display:flex;align-items:flex-start;padding:var(--space-50) var(--space-100);border-radius:var(--radius-2);gap:var(--space-100)}copilot-log-panel .row.information{background-color:var(--blue-50)}copilot-log-panel .row.warning{background-color:var(--yellow-50)}copilot-log-panel .row.error{background-color:var(--red-50)}copilot-log-panel .type{margin-top:var(--space-25)}copilot-log-panel .type.error{color:var(--red)}copilot-log-panel .type.warning{color:var(--yellow)}copilot-log-panel .type.info{color:var(--color)}copilot-log-panel .message{display:flex;flex-direction:column;flex-grow:1;gap:var(--space-25);overflow:hidden}copilot-log-panel .message>*:not(.expanded){white-space:nowrap;overflow:hidden;text-overflow:ellipsis;width:100%}copilot-log-panel .message>.expanded{overflow-wrap:break-word}copilot-log-panel .firstrow{display:flex;align-items:baseline;gap:.5em;flex-direction:column}copilot-log-panel .firstrowmessage{width:100%;overflow:hidden;text-overflow:ellipsis}copilot-log-panel button{padding:0;border:0;background:transparent}copilot-log-panel svg{height:12px;width:12px}copilot-log-panel .secondrow,copilot-log-panel .timestamp{font-size:var(--font-size-0);line-height:var(--line-height-1)}copilot-log-panel .expand span{height:12px;width:12px}";
var A = Object.defineProperty, B = Object.getOwnPropertyDescriptor, h = (e, t, a, o) => {
  for (var s = o > 1 ? void 0 : o ? B(t, a) : t, d = e.length - 1, i; d >= 0; d--)
    (i = e[d]) && (s = (o ? i(t, a, s) : i(s)) || s);
  return o && s && A(t, a, s), s;
};
class _ {
  constructor() {
    this.showTimestamps = !1, C(this);
  }
  toggleShowTimestamps() {
    this.showTimestamps = !this.showTimestamps;
  }
}
const g = new _();
let r = class extends I {
  constructor() {
    super(...arguments), this.unreadErrors = !1, this.messages = [], this.nextMessageId = 1, this.transitionDuration = 0, this.errorHandlersAdded = !1;
  }
  connectedCallback() {
    super.connectedCallback(), this.onCommand("log", (e) => {
      this.handleLogEventData({ type: e.data.type, message: e.data.message });
    }), this.onEventBus("log", (e) => this.handleLogEvent(e)), this.onEventBus("update-log", (e) => this.updateLog(e.detail)), this.onEventBus("notification-shown", (e) => this.handleNotification(e)), this.onEventBus("clear-log", () => this.clear()), this.reaction(
      () => M.sectionPanelResizing,
      () => {
        this.requestUpdate();
      }
    ), this.transitionDuration = parseInt(
      window.getComputedStyle(this).getPropertyValue("--dev-tools-transition-duration"),
      10
    ), this.errorHandlersAdded || (T((e) => {
      this.log(e.type, e.message, !!e.internal, e.details, e.link);
    }), w.forEach((e) => {
      this.log(e.type, e.message, !!e.internal, e.details, e.link);
    }), w.length = 0, this.errorHandlersAdded = !0);
  }
  clear() {
    this.messages = [];
  }
  handleNotification(e) {
    this.log(e.detail.type, e.detail.message, !0, e.detail.details, e.detail.link);
  }
  handleLogEvent(e) {
    this.handleLogEventData(e.detail);
  }
  handleLogEventData(e) {
    this.log(
      e.type,
      e.message,
      !!e.internal,
      e.details,
      e.link,
      p(e.expandedMessage),
      p(e.expandedDetails),
      e.id
    );
  }
  activate() {
    this.unreadErrors = !1, this.updateComplete.then(() => {
      const e = this.renderRoot.querySelector(".message:last-child");
      e && e.scrollIntoView();
    });
  }
  render() {
    return l`<style>
        ${q}
      </style>
      ${this.messages.map((e) => this.renderMessage(e))} `;
  }
  renderMessage(e) {
    let t, a, o;
    return e.type === c.ERROR ? (t = "error", o = n.exclamationMark, a = "Error") : e.type === c.WARNING ? (t = "warning", o = n.warning, a = "Warning") : (t = "info", o = n.info, a = "Info"), e.internal && (t += " internal"), l`
      <div
        data-id="${e.id}"
        class="row ${e.type} ${e.details || e.link ? "has-details" : ""}">
        <span class="type ${t}" title="${a}">${o}</span>
        <div class="message" @click=${() => this.toggleExpanded(e)}>
          <span class="firstrow">
            <span class="timestamp" ?hidden=${!g.showTimestamps}>${U(e.timestamp)}</span>
            <span class="firstrowmessage"
              >${e.expanded && e.expandedMessage ? e.expandedMessage : e.message}
            </span>
          </span>
          ${e.expanded ? l` <span class="secondrow expanded">${e.expandedDetails ?? e.details}</span>` : l`<span class="secondrow" ?hidden="${!e.details && !e.link}"
                >${p(e.details)}
                ${e.link ? l`<a class="ahreflike" href="${e.link}" target="_blank">Learn more</a>` : ""}</span
              >`}
        </div>
        <button
          aria-label="Expand details"
          theme="icon tertiary"
          class="expand"
          @click=${() => this.toggleExpanded(e)}
          ?hidden=${!this.canBeExpanded(e)}>
          <span>${e.expanded ? n.chevronDown : n.chevronRight}</span>
        </button>
      </div>
    `;
  }
  log(e, t, a, o, s, d, i, E) {
    const k = this.nextMessageId;
    this.nextMessageId += 1, i || (i = t);
    const f = {
      id: k,
      type: e,
      message: t,
      details: o,
      link: s,
      dontShowAgain: !1,
      deleted: !1,
      expanded: !1,
      expandedMessage: d,
      expandedDetails: i,
      timestamp: /* @__PURE__ */ new Date(),
      internal: a,
      userId: E
    };
    for (this.messages.push(f); this.messages.length > r.MAX_LOG_ROWS; )
      this.messages.shift();
    return this.requestUpdate(), this.updateComplete.then(() => {
      const m = this.renderRoot.querySelector(".message:last-child");
      m ? (setTimeout(() => m.scrollIntoView({ behavior: "smooth" }), this.transitionDuration), this.unreadErrors = !1) : e === c.ERROR && (this.unreadErrors = !0);
    }), f;
  }
  updateLog(e) {
    let t = this.messages.find((a) => a.userId === e.id);
    t || (t = this.log(c.INFORMATION, "<Log message to update was not found>", !1)), Object.assign(t, e), S(t.expandedDetails) && (t.expandedDetails = p(t.expandedDetails)), this.requestUpdate();
  }
  updated() {
    const e = this.querySelector(".row:last-child");
    e && this.isTooLong(e.querySelector(".firstrowmessage")) && e.querySelector("button.expand")?.removeAttribute("hidden");
  }
  toggleExpanded(e) {
    this.canBeExpanded(e) && (e.expanded = !e.expanded, this.requestUpdate()), L("use-log", { source: "toggleExpanded" });
  }
  canBeExpanded(e) {
    if (e.expandedMessage || e.expanded)
      return !0;
    const t = this.querySelector(`[data\\-id="${e.id}"]`)?.querySelector(
      ".firstrowmessage"
    );
    return this.isTooLong(t);
  }
  isTooLong(e) {
    return e && e.offsetWidth < e.scrollWidth;
  }
};
r.MAX_LOG_ROWS = 1e3;
h([
  $()
], r.prototype, "unreadErrors", 2);
h([
  $()
], r.prototype, "messages", 2);
r = h([
  y("copilot-log-panel")
], r);
let x = class extends D {
  createRenderRoot() {
    return this;
  }
  connectedCallback() {
    super.connectedCallback(), this.style.display = "flex";
  }
  render() {
    return l`
      <button title="Clear log" aria-label="Clear log" theme="icon tertiary">
        <span
          @click=${() => {
      R.emit("clear-log", {});
    }}
          >${n.trash}</span
        >
      </button>
      <button title="Toggle timestamps" aria-label="Toggle timestamps" theme="icon tertiary">
        <span
          class="${g.showTimestamps ? "on" : "off"}"
          @click=${() => {
      g.toggleShowTimestamps();
    }}
          >${n.clock}</span
        >
      </button>
    `;
  }
};
x = h([
  y("copilot-log-panel-actions")
], x);
const b = {
  header: "Log",
  expanded: !0,
  panelOrder: 0,
  panel: "bottom",
  floating: !1,
  tag: "copilot-log-panel",
  actionsTag: "copilot-log-panel-actions"
}, P = {
  init(e) {
    e.addPanel(b);
  }
};
window.Vaadin.copilot.plugins.push(P);
const v = { hour: "numeric", minute: "numeric", second: "numeric", fractionalSecondDigits: 3 };
let u;
try {
  u = new Intl.DateTimeFormat(navigator.language, v);
} catch (e) {
  console.error("Failed to create date time formatter for ", navigator.language, e), u = new Intl.DateTimeFormat("en-US", v);
}
function U(e) {
  return u.format(e);
}
export {
  x as Actions,
  r as CopilotLogPanel
};
