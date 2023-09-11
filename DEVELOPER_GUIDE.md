# Developer guide

This document is intended to provide information about the project structure, best practices and other useful information for contributors.

## Prerequisites

The languages and tools used in this project are:

- Java 17.0.8-tem (install with sdkman)
- Maven 3.9.4

<details>
<summary>VSCode extensions (you probably already have these installed)</summary>
Here is a list of VSCode extensions needed for this project (If you open this project in VSCode and want QOL improvements) (These extensions should have already been installed from TDT4100):

- Extension Pack for Java
- SceneBuilder extension for Visual Studio Code
- (Recommended) Todo Tree
- (Recommended) GitLens
</details>
<br />

Please ensure you have the correct versions of these tools installed before continuing. 
<br />

I have also edited the settings.json file such that auto-format and organize imports are enabled on save. This is to ensure that the code style is consistent across the project. \
These settings have been added (you do not need to do anything to get this added):
```
"java.cleanup.actionsOnSave": [
    "addOverride"
],
"java.saveActions.organizeImports": true,
"java.completion.importOrder": [
    "#",
    "java",
    "javax",
    "org",
    "com",
    ""
],
"[java]": {
    "editor.formatOnSave": true,
    "editor.defaultFormatter": "redhat.java",
    "editor.codeActionsOnSave": {
        "source.organizeImports": true
    },
},
```

## Project structure

This project follows the Model-View-Controller (MVC) pattern. \
All code for the **model** is located in the [model package](/modules-template/core/src/main/java/core/). \
All code for the **view** is located in the [resources/ui folder](/modules-template/ui/src/main/resources/ui/). \
All code for the **controller** is located in the [ui package](/modules-template/ui/src/main/java/ui/). \
\
Not sure if this is the best way to structure the project, I may change this. We need to discuss this.

## Run the code

Make sure you are in the [ui folder](/modules-template/ui/) when running these commands (`cd modules-template/ui/`)

Compile code:
```
mvn compile
```

Run tests:
```
mvn test
```

Run the application:
```
mvn javafx:run
```

## Branching

You should always make changes on a branch (never make direct changes to the `master` branch). A new branch should be created for work on a new issue. The branch name should be the issue number followed by a short description of the issue. For example, if you are working on issue #1, you should create a branch named `1-implementing-new-feature`. This will ensure that the branch is automatically linked to that issue, and add some QOL improvements when creating pull requests.

### Creating a new branch
This is a step by step guide on how to create a new branch. You first need to be on a branch in order to branch out from it (often just the `master` branch). Then you write the command `git checkout -b <branch-name>`. This will create a new branch with the name you specified, and automatically switch to that branch. You can now make changes to the code, and commit them to the branch. 

### Updating a branch with content in `master`
Before you create a pull request, you should make sure that your branch is up to date with the `master` branch. This is to ensure that there are no merge conflicts when you create the pull request. \
The code block below assumes you start on your own branch and want to update it with main.
```
(On your own branch)
> git checkout master

(Now you are on the main branch)
> git pull
> git checkout <your-branch-name>

(Now you are on your own branch again)
> git merge master

(There may be merge conflicts here, if so, resolve them (resolving them in VSCode is recommended))

(Now you can push your changes to remote without any merge conflicts)
> git push
```

## Pull requests

When you are done with your changes, and want to merge your branch into `master`, you will need to create a Pull Request (PR). This is done by going to the "Pull requests" tab on GitHub, and clicking the "New pull request" button. You will then be presented with a page where you can select the branch you want to merge into `master`, and the branch you want to merge from. You can then click the "Create pull request" button. You will then be presented with a page where you can write a description of the changes you have made. In this repository you can approve your own PR, and merge it into `master`, without any other reviewers. If you want others to review your PR, you can add them as reviewers.

When the review is done, you are ready to merge into `master`. Click the "Merge pull request" button, and then the "Confirm merge" button (pick the default option (not squash merge)). You have now merged your branch into `master`, and your changes are now live.


## Note
Note: I have not used GitLab extensively, so I am not completely sure how the workflow is there. I wrote this `DEVELOPER_GUIDE` with GitHub in mind, but I assume the workflow is similar on GitLab. If there is any devitation from the GitHub workflow, please update this guide.

## Best practices

### Naming conventions ([link](https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html))
- Class and interfaces names should be in `PascalCase`
- Method names should be in `camelCase`
- Variable names should be in `camelCase`
- Constant names should be in `UPPER_CASE_WITH_UNDERSCORES`

### Other best practices
- Always use `this` when referring to class variables (I believe this is automated with the settings.json file)
- You should never use "[magic numbers](https://stackoverflow.com/questions/47882/what-is-a-magic-number-and-why-is-it-bad)", instead create a constant variable and reference it instead, this makes the code clearer and easier to read.
- You should create Javadoc comments for each class, where you explain it's use and functionality.
![Alt text](image-3.png)
- If you find yourself nesting a lot of if-statements, you should consider using the [guard clause](https://codingbeautydev.com/blog/stop-using-nested-ifs/?expand_article=1) pattern (invert the if statement and return early).
