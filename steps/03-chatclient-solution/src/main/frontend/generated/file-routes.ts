import type { AgnosticRoute } from "@vaadin/hilla-file-router/types.js";
import { createRoute } from "@vaadin/hilla-file-router/runtime.js";
import * as Page0 from "../views/@index.js";
import * as Page1 from "../views/Chat/Chat.js";
import * as Page3 from "../views/Message/Message.js";
import * as Page5 from "../views/PromptInput/PromptInput.js";
const routes: readonly AgnosticRoute[] = [
    createRoute("", false, Page0),
    createRoute("Chat", false, [
        createRoute("Chat", false, Page1)
    ]),
    createRoute("Message", false, [
        createRoute("Message", false, Page3)
    ]),
    createRoute("PromptInput", false, [
        createRoute("PromptInput", false, Page5)
    ])
];
export default routes;
