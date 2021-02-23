import chalk from "chalk";
import fs from "fs";
import ncp from "ncp";
import path from "path";
import { promisify } from "util";
import execa from "execa";
import Listr from "listr";
import { projectInstall } from "pkg-install";

const access = promisify(fs.access); //for accessing files
const copy = promisify(ncp); //copy template file in newly created projectName directory

async function copyTemplateFiles(options) {
  return copy(options.templateDirectory, options.targetDirectory, {
    clobber: false,
  });
}

//initialize git in projectName directory
async function initGit(options) {
  const result = await execa("git", ["init"], {
    cwd: options.targetDirectory,
  });
  if (result.failed) {
    return Promise.reject(new Error("Failed to initialize git"));
  }
  return;
}

export async function createProject(options) {
  options = {
    ...options,
    targetDirectory: options.projectPath,
  };

  const templateDir = path.resolve(
    new URL(import.meta.url).pathname,
    "../../templates",
    options.template
  );

  options.templateDirectory = templateDir;

  try {
    await access(templateDir, fs.constants.R_OK);
  } catch (err) {
    console.error("Template doesn't exists!", chalk.red.bold("ERROR"));
    process.exit(1);
  }

  const tasks = new Listr([
    {
      title: "Copy project files",
      task: () => copyTemplateFiles(options),
    },
    {
      title: "Initialize git",
      task: () => initGit(options),
      enabled: () => options.git,
    },
    {
      title: "Install dependencies",
      task: () =>
        projectInstall({
          cwd: options.targetDirectory,
        }),
    },
  ]);

  await tasks.run();

  var link = null;

  console.log(
    chalk.green.bold(`${options.projectName} successfully initialized.`)
  );
  if (options.template === "keechma-next-web-template") {
    link = "https://github.com/VeryBigThings/keechma-next-web-template";
  } else {
    link = "https://github.com/VeryBigThings/keechma-next-mobile-skeleton";
  }

  console.log(`More info: ${link}`);
  console.log(chalk.white.bold("Happy coding!"));
  return true;
}
