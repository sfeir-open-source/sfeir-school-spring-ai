import { SfeirThemeInitializer } from '../web_modules/sfeir-school-theme/sfeir-school-theme.mjs';

// One method per module
function schoolSlides() {
  const directory = '00-school/';
  return [
    `${directory}00-welcome.md`,
    `${directory}01-speakers.md`,
    `${directory}02-prerequisites.md`,
    `${directory}03-overview.md`];
}

function iaSlides() {
  const directory = '10-artificial-intelligence/';
  return [
    //
    `${directory}00-definition.md`,
    `${directory}01-gen-ai.md`,
    `${directory}02-transformer-concept.md`,
    `${directory}04-labs.md`
  ];
}

function springIASlides() {
  const directory = '20-spring-ai/';
  return [
    //
    `${directory}00-goal.md`,
    `${directory}01-project-life.md`,
    `${directory}02-features.md`,
    `${directory}03-labs.md`
  ]
}


function formation() {
  return [
    //
    ...schoolSlides(), //
    ...iaSlides(), //
    ...springIASlides(), //
  ].map((slidePath) => {
    return { path: slidePath };
  });
}

SfeirThemeInitializer.init(formation);
