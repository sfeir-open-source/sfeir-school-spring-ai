import { SfeirThemeInitializer } from '../web_modules/sfeir-school-theme/dist/sfeir-school-theme.mjs';

// One method per module
function schoolSlides() {
  const directory = '00-school/';
  return [
    `${directory}00-welcome.md`,
    `${directory}01-speakers.md`,
    `${directory}02-prerequisites.md`,
    `${directory}03-overview.md`];
}
function introSlides() {
  const directory = '01-intro/';
  return [
    //
    `${directory}00-definition.md`,
    `${directory}02-transformer-concept.md`,
    `${directory}03-ia-concept.md`,
    `${directory}10-goal.md`,
    `${directory}11-features.md`
  ];
}
function ollamaSlides() {
  const directory = '02-ollama-and-vertex/';
  return [
    //
    `${directory}00-TITLE.md`,
    `${directory}01-llm.md`
  ]
}
function initialisationSlides() {
  const directory = '03-initialisation/';
  return [
    //
    `${directory}00-TITLE.md`,
    `${directory}01-initialisation.md`,
    `${directory}02-lab-initialisation.md`
  ]
}
function promptTemplatingSlides() {
  const directory = '04-prompt-templating/';
  return [
    //
    `${directory}00-TITLE.md`,
    `${directory}01-prompt-templating.md`,
    `${directory}02-lab-prompt-templating.md`
  ]
}
function chatClientSlides() {
  const directory = '05-chat-client/';
  return [
    //
    `${directory}00-TITLE.md`,
    `${directory}01-chat-client.md`,
    `${directory}02-lab-chat-client.md`
  ]
}
function ragSlides() {
  const directory = '06-rag/';
  return [
    //
    `${directory}00-TITLE.md`,
    `${directory}01-rag.md`,
    `${directory}02-lab-rag.md`
  ]
}
function toolsCallingSlides() {
  const directory = '07-tools-calling/';
  return [
    //
    `${directory}00-TITLE.md`,
    `${directory}01-tools-calling.md`,
    `${directory}02-lab-tools-calling.md`
  ]
}
function mcpSlides() {
  const directory = '08-mcp/';
  return [
    //
    `${directory}00-TITLE.md`,
    `${directory}01-what-is-mcp.md`,
    `${directory}02-lab-mcp.md`,
    `${directory}03-mcp-more.md`
  ]
}
function conclusionSlides() {
  const directory = '09-conclusion/';
  return [
    //
    `${directory}00-TITLE.md`,
    `${directory}01-conclusion.md`
  ]
}


function formation() {
  return [
    //
    ...schoolSlides(), //
    ...introSlides(), //
    ...ollamaSlides(), //
    ...initialisationSlides(), //
    ...promptTemplatingSlides(), //
    ...chatClientSlides(), //
    ...ragSlides(), //
    ...toolsCallingSlides(), //
    ...mcpSlides(), //
    ...conclusionSlides() //
  ].map((slidePath) => {
    return { path: slidePath };
  });
}

SfeirThemeInitializer.init(formation);
