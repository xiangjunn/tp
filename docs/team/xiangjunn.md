---
layout: page
title: Xiang Jun's Project Portfolio Page
---

### Project: SoConnect

SoConnect is a **desktop app for SoC students to manage contacts of Professors and Teaching Assistants,
as well as to keep track of noteworthy events, optimized for use via a _Command Line Interface (CLI)_** while still
having the benefits of a _Graphical User Interface (GUI)_. This project is part of the
[CS2103T](https://nus-cs2103-ay2122s1.github.io/website/) team project requirements for AY2021/2022 Semester 1.

Given below are my notable contributions to the project.

* **New Feature**: Added the ability to link an event to one or more contacts.
    * What it does: allows the user to choose an event and link related contacts to it.
    * Justification: An event is likely to have people that are hosting it. Therefore, the ability to view the contacts
      of an event allows the user to easily contact the group of people that are hosting the event. This feature
      connects contact and event together, making the product more cohesive.
    * Highlights: This enhancement affects existing commands such as EDeleteCommand, CEditCommand and EClearCommand.
      What these commands have in common is that they change the contacts/events in the contact/event list after
      execution. This is problematic because these can result in change in the links as well. Therefore, the
      implementation was challenging as it required changes to existing commands to take into account the links as well.

* **New Feature**: Added the ability to delete a range of contacts.
    * What it does: allows the user to delete either one contact or an inclusive range of consecutive contacts.
    * Justification: It is troublesome for users to delete the contact one by one. Allowing them to delete a range of contacts
        is more user-friendly.
    * Highlights: This feature works well with the existing feature which can filter the contact list because user can delete the contacts from that filtered list with ease.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=xiangjunn&tabRepo=AY2122S1-CS2103T-W15-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

* **Project management**:
    * Add branch protection rules to enforce certain requirements.
      * Ensures that a branch has to pass CI before it can be merged into the `master` branch.
      * Requires at least one reviewer's approval before pull request reviews can be merged.
      * Ensures that a branch is up to date with `master` branch before it can be merged.
      * Disallow push commits to `master` branch directly.
    * Makes sure that team members are following the proper way of reviewing and merging pull requests.
    * Facilitate closing of milestones and tagging the relevant commits with the correct version.

* **Enhancements to existing features**:
    * Improvements to the GUI (Pull requests [\#45](https://github.com/AY2122S1-CS2103T-W15-3/tp/pull/45), [\#149](https://github.com/AY2122S1-CS2103T-W15-3/tp/pull/149))
    * Enhance CDeleteCommand to allow for deleting over a range of values. Creates a range class for this purpose. (Pull requests [\#87](https://github.com/AY2122S1-CS2103T-W15-3/tp/pull/87))

* **Documentation**:
    * User Guide:
        * Added documentation for the features `cdelete`, `cclear`, `eadd` and `elist` (Pull request [\#19](https://github.com/AY2122S1-CS2103T-W15-3/tp/pull/19))
        * Added documentation for the feature `elink` (Pull request [\#131](https://github.com/AY2122S1-CS2103T-W15-3/tp/pull/131))
    * Developer Guide:
        * Added documentation for product scope and user stories (Pull request [\#33](https://github.com/AY2122S1-CS2103T-W15-3/tp/pull/33))
        * Added implementation details of the `elink` feature, which include sequence diagram, activity diagram and design considerations.
        * Added instruction for manual testing for `elink` feature.
        * Added use cases for editing events and linking an event to one or more contacts 

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#51](https://github.com/AY2122S1-CS2103T-W15-3/tp/pull/51), [\#142](https://github.com/AY2122S1-CS2103T-W15-3/tp/pull/142)
    * Reported bugs and suggestions for other teams in the class (examples: [1](https://github.com/AY2122S1-CS2103-T16-2/tp/issues/160), [2](https://github.com/AY2122S1-CS2103-T16-2/tp/issues/145), [3](https://github.com/AY2122S1-CS2103-T16-2/tp/issues/159))
