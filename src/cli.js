import arg from "arg";
import chalk from "chalk";
import inquirer from "inquirer";
import { createProject } from "./main";
import fs from "fs";

function parseArgumentsIntoOptions(rawArgs) {
  const args = arg(
    {
      "--git": Boolean,
      "--yes": Boolean,
      "--install": Boolean,
      "-g": "--git",
      "-y": "--yes",
      "-i": "--install",
    },
    {
      argv: rawArgs.slice(2),
    }
  );
  return {
    skipPrompts: args["--yes"] || false,
    git: args["--git"] || false,
    projectName: args._[0],
    runInstall: args["--install"] || false,
  };
}
async function promptForOptions(options) {
  const questions = [];

  if (!options.projectName) {
    return Promise.reject(
      console.error(
        "%s Failed to initialize project. Project name is undefined!",
        chalk.red.bold("ERROR")
      )
    );
  }

  if (!options.git) {
    questions.push({
      type: "confirm",
      name: "git",
      message: "Initialize a git repository?",
      default: false,
    });
  }

  const answers = await inquirer.prompt(questions);
  return {
    ...options,
    git: options.git || answers.git,
  };
}

function createProjectDirectory(options) {
  if (!fs.existsSync(options.projectName)) {
    fs.mkdirSync(options.projectName);
  } else {
    throw new Error(
      `%s Directory with name ${options.projectName} already exists!`
    );
  }
}

export async function cli(args) {
  try {
    let options = parseArgumentsIntoOptions(args);
    createProjectDirectory(options);
    options = await promptForOptions(options);
    //await createProject(options)
  } catch (error) {
    console.error(error.message, chalk.red.bold("ERROR"));
  }
}
