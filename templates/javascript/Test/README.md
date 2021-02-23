## VBT Stuff

**Foreword** 

_Purpose of this repo is to test out a project structure, development processes and workflows to determine the template which best suits our day-to-day needs in the agency - one can expect breaking changes will be introduced until we work out the kinks_

---
### Basics
1. Change the name of the project in `package.json`
2. Run `git config core.hooksPath .githooks` to set `pre-commit` hooks (yarn lint and yarn format)
3. Update CODEOWNERS file accordingly (with project leads)

---

### Release Management

Idea behind this setup is to automate as much workflow as possible, while trying to reduce human error to minimum.
Releasing, tagging and changelog updates are done via Git Actions when `push to master` occurs. Outcome is determined
by comparing closed Pull Requests that have happened since last tag was made. Some defaults are implemented, but can
be further fine-tuned by leveraging PR label mechanism.

#### Tagging and Releasing
 * Based on [Semver](http://semver.org/)
 * Flexible version bumping scheme with a project default or overrides using Pull Request Labels
 * Creates Release Notes automatically (with a list of commits since the last release)
 * PR labels `release:major`, `release:minor` and `release:patch` determine the Semver version bump (`major.minor.patch`)
 * Closed PR's since last Tag are used for consideration
 * default is `release:minor`

 Workflow examples:

 ```
       v1.0.0     v1.1.0                   v1.1.1
        |          |                        |
 - - ---o----------o------------------------o     master branch
                    \                      /
                     \                    /    PR [release:patch]
                      \                  /
                       o--------o-------o     bug-fix-branch
```

 ```
       v1.0.0     v1.1.0                                         v2.0.0
        |          |                                              |
 - - ---o----------o----------------------------------------------o     master branch
                    \                                            /
                     \                                          /     merge ** // see footnote
                      \                                        /
                       o------------o-------------------------o     feature-foo
                        \          /                         /
                         \        /  PR [release:patch]     /
                          \      /                         /
                           o----o  feature-foo-1          /
                            \                            /
                             \                          /     PR [release:major]
                              \                        /
                               o----------------------o     feature-foo-2
```
** merge - _since all the PR's are used for consideration, the one made to commit code to master would also be used (with no labels applied, default [minor] would be used which could influence the final outcome)_

 
#### Changelog Generator
 * based on [Github Changelog Generator](https://github.com/github-changelog-generator/github-changelog-generator)
 * Generates a changelog file based on tags, issues and merged pull requests (and splits them into separate lists according to labels)
 * Labels `bug`, `enhancement` and `documentation` are used to sort changes in sections, others are grouped together

Workflow example:
 ```
       v1.0.0     v1.1.0                                                     v2.0.0
        |          |                                                          |
 - - ---o----------o----------------------------------------------------------o     master branch
                    \                                                        /
                     \                                                      /
                      \                                                    /
                       o------------o-------------------------------------o     feature-foo
                        \          /                                     /
                         \        /  PR [release:minor enhancement]     /
                          \      /                                     /
                           o----o  feature-foo-1                      /
                            \                                        /
                             \                                      /     PR [release:major bug]
                              \                                    /
                               o----------------------------------o     feature-foo-2
``` 

```
v2.0.0 (2020-08-17)
Full Changelog

Implemented enhancements:

feature-foo-1 #1 (tiborkr)

Fixed bugs:

feature-foo-2 #2 (tiborkr)
```

---

## Available Scripts

In the project directory, you can run:

### `yarn start`

Runs the app in development mode.<br>
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.
The page will reload if you make edits.

The app uses [Reagent](https://reagent-project.github.io), a minimalistic interface between ClojureScript and React.<br>
You can use existing npm React components directly via a [interop call](http://reagent-project.github.io/docs/master/InteropWithReact.html#creating-reagent-components-from-react-components).

Builds use [Shadow CLJS](https://github.com/thheller/shadow-cljs) for maximum compatibility with NPM libraries. You'll need a [Java SDK](https://adoptopenjdk.net/) (Version 8+, Hotspot) to use it. <br>
You can [import npm libraries](https://shadow-cljs.github.io/docs/UsersGuide.html#js-deps) using Shadow CLJS. See the [user manual](https://shadow-cljs.github.io/docs/UsersGuide.html) for more information.

### `yarn cards`

Runs the interactive live development enviroment.<br>
You can use it to design, test, and think about parts of your app in isolation.

Use [http://localhost:3000/workspaces.html](http://localhost:3000/workspaces.html) to inspect.

This environment uses [Workspaces](https://github.com/nubank/workspaces) and [React Testing Library](https://testing-library.com/docs/react-testing-library/intro).

### `yarn build`

Builds the app for production to the `public` folder.<br>
It correctly bundles all code and optimizes the build for the best performance.

Your app is ready to be deployed!

## Other useful scripts

### `null` and `yarn e2e`

You can use `null` to run tests a single time, and `yarn e2e` to run the end-to-end test app.
`yarn test` launches tests in interactive watch mode.<br>

See the ClojureScript [testing page](https://clojurescript.org/tools/testing) for more information. E2E tests use [Taiko](https://github.com/getgauge/taiko) to interact with a headless browser.

### `yarn lint` and `yarn format`

`yarn lint` checks the code for known bad code patterns using [clj-kondo](https://github.com/borkdude/clj-kondo).

`yarn format` will format your code in a consistent manner using [zprint-clj](https://github.com/clj-commons/zprint-clj).

### `yarn report`

Make a report of what files contribute to your app size.<br>
Consider [code-splitting](https://code.thheller.com/blog/shadow-cljs/2019/03/03/code-splitting-clojurescript.html) or using smaller libraries to make your app load faster.

### `yarn server`

Starts a Shadow CLJS background server.<br>
This will speed up starting time for other commands that use Shadow CLJS.

## Useful resources

Clojurians Slack http://clojurians.net/.

CLJS FAQ (for JavaScript developers) https://clojurescript.org/guides/faq-js.

Official CLJS API https://cljs.github.io/api/.

Quick reference https://cljs.info/cheatsheet/.

Offline searchable docs https://devdocs.io/.

VSCode plugin https://github.com/BetterThanTomorrow/calva.

This project was bootstrapped with [Create CLJS App](https://github.com/filipesilva/create-cljs-app).

