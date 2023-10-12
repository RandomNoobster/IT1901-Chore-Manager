# General Workflow
We have continued to hold the two-hours meeting every week. However the two, two-hour meetings turned into four-hour meetings after the lectures stopped. These meetings are where we have done the big majority of the work. The first meetings after deliverable 1 were spent adressing the feedback from the first deliverable. After that we have spent the meetings working on the issues we have created for the project. 

# Issues
According to the feedback from the first deliverable and the requirements for the second deliverable we have created issues in the same manner as for deliverable 1. We have created issues for all the requirements for the second deliverable. We have also created issues for the feedback from the first deliverable. This has been the foundation for our work for deliverable 2. In addition we have adressed several issues in regards to the UI and color schemes as well as testing and documentation. 

# Branching
Although branching didnt become a requirement until this second deliverable, we have been using branching since the beginning of the project. We have continued to use the same branching strategy as described in the first deliverable and the [Developer Guide](/DEVELOPER_GUIDE.md). This means that we have a master branch which is the main branch of the project. All issues are branced into their own branch to minimize conflicts and securing that the master branch is always working. In addition we have made the `master` branch protected, to avoid accidental commits. When an issue is finished we create a merge request. Then the other team members review the code, suggest changes, and approve. When at least one other have approved, the branch can be merged into `master`. We have used a strict naming convention for the branches. The name of the branch is the issue number followed by a short description of the issue. This makes it easy to see which issue a branch is related to. Naming convention for branches: `{issue-number}-name-of-branch`.
This is documented in the [Developer guide](/DEVELOPER_GUIDE.md).

# Milestones
We have created milestones for the project. The milestones are used to group issues together. We have created milestones for the different deliverables and for the feedback from the first deliverable. This makes it easy to see which issues are related to which deliverable. All our merge-requests are also grouped in the individual milestones. This has helped us to keep a clean record of when new features has been added to the project in regards to the different deliverables. 

# Pair-Programming
We have also implemented some pair-programming in our workflow for some of the issues. This has been done by working together in pairs on the same computer. This has been done for issues where we have felt that it would be easier to work together on the same computer. Especially when working with different scenes and how the view should look like for the user. In these situations it has been good with different perspectives as everyone has a different perception of what "looks good" and what is user friendly. 

# Code-quality
We have setup an automatic formatter which uses RedHat's default formatting settings. The code is formatted automatically on save, this helps to keep the code style consistent, and makes the development easier, as the developer has less things on their mind. In addition to the formatter, we have also setup other actions to be performed on save:
- `organizeImports` (add imports + reorganize import order)
- `qualifyMembers` (automatically add `this` on attributes)
- `addOverride` (automatically add `@Override` to methods that override a superclass method)

The linters we use are Checkstyle and Spotbugs. 
For more information about linters see [release2's README](/docs/release2/README.md).

We have also extensively followed best practices in Java, you can see some of them in the [Developer Guide](/DEVELOPER_GUIDE.md). 
To ensure a consistent code structure we used the MVC-principle, which means that the model, view and controller are separated.


