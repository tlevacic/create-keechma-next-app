import arg from "arg";
import chalk from "chalk";
import inquirer from "inquirer";
import path from "path";
import { createProject } from "./main";
import fs from "fs";

//inputs from CLI
function parseArgumentsIntoOptions(rawArgs) {
  const args = arg(
    {
      "--git": Boolean,
      "-g": "--git",
    },
    {
      argv: rawArgs.slice(2),
    }
  );
  return {
    git: args["--git"] || false,
    projectName: args._[0],
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

  //wait for user to answer above questions
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
  return {
    ...options,
    projectPath: `${process.cwd()}/${options.projectName}`,
  };
}

export async function cli(args) {
  try {
    let options = parseArgumentsIntoOptions(args);
    options = createProjectDirectory(options);
    options = await promptForOptions(options);
    await createProject(options);
  } catch (error) {
    console.error(error.message, chalk.red.bold("ERROR"));
  }
}
