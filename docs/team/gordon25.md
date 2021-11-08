---
layout: page
title: Gordon Yit Hongyao's Project Portfolio Page
---

### Project: SoConnect

SoConnect is a **desktop app for SoC students to manage contacts of Professors and Teaching Assistants, as well as to keep track of noteworthy events, optimized for use via a _Command Line Interface (CLI)_** while still having the benefits of a _Graphical User Interface (GUI)_. 

This project is part of the [CS2103T](https://nus-cs2103-ay2122s1.github.io/website/) team project requirements for AY2021/2022 Semester 1.

Given below are my contributions to the project.

* **New Feature**: Added the ability to mark contacts and events.
    * What it does: allows the user to mark one or more contacts / events at a time. The marked contacts / events will appear at the top of the contact / event list.
    * Justification: This feature improves the product significantly because it saves the user time from having to scroll through the list or using the `find` command to find contacts / events that they use frequently and the app should provide a convenient way for users to refer to them.
    * Highlights: This enhancement affects existing commands commands to be added in future. It required an in-depth analysis of design alternatives.

* **New Feature**: Added ability to unmark contacts and events.
   * What it does: allows the users to unmark one or more marked contacts / events at a time. The newly unmarked contacts / events will appear after all marked contacts / events in the list.
    * Justification: This feature improves the product significantly because it allows the user to keep the number of marked contacts / events small and unmark contacts / events they no longer reference frequently, saving them time from having to look through a long list of marked contacts / events.
    * Highlights: This enhancement affects existing commands commands to be added in future. It required an in-depth analysis of design alternatives.

<div style="page-break-after: always;"></div>

* **New Feature**: Added ability to search contacts / events based on other fields 
   * What it does: allows the users to search for contacts / events based on other fields other than the the contact / event name.
    * Justification: This feature improves the product significantly because it gives users more freedom in how they search for contacts / events. For example, they can for search for an event based on its location or description.
    * Highlights: This enhancement affects existing commands commands to be added in future. It required an in-depth analysis of design alternatives. The implementation too was challenging as it required changes to existing commands.

* **New Feature**: Added ability to list only certain fields of events 
   * What it does: allows the users to only list certain types of fields of each event. 
    * Justification: This feature improves the product significantly because it allows user to focus on certain fields of events they are interested in instead of looking at the entire list.
    * Highlights: This enhancement affects existing commands commands to be added in future. It required an in-depth analysis of design alternatives. The implementation too was challenging as it required changes to existing commands.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=w15-3&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=zoom&tabAuthor=janjanchen&tabRepo=AY2122S1-CS2103T-W15-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&zA=janjanchen&zR=AY2122S1-CS2103T-W15-3%2Ftp%5Bmaster%5D&zACS=199.78947368421052&zS=2021-09-17&zFS=w15-3&zU=2021-11-06&zMG=false&zFTF=commit&zFGS=groupByRepos&zFR=false)

* **Project management**:
    * Managed issue tracker, created issues, linked them to the relevant milestone, tags and project dashboard and assigned them to the relevant teammate based on the breakdown of work discussed in the team meeting. Where possible, included task descriptions in the issue for easy reference.

* **Enhancements to existing features**:
    * Updated contact search features to allow users to find by other fields: [\#135](https://github.com/AY2122S1-CS2103T-W15-3/tp/issues/135)
    * Modified storage class to store events: [\#53](https://github.com/AY2122S1-CS2103T-W15-3/tp/issues/53)

* **Documentation**:
    * User Guide: 
         * Added documentation for the features `eedit`, `efind` and `edelete`: [\#29](https://github.com/AY2122S1-CS2103T-W15-3/tp/issues/29)
         * Updated documentation for the features `cmark`, `cunmark`, added screenshots for `edelete`, `efind`, `elist`: [\#213](https://github.com/AY2122S1-CS2103T-W15-3/tp/issues/213)
    * Developer Guide:
        * Added implementation details for `elist` [\#132](https://github.com/AY2122S1-CS2103T-W15-3/tp/issues/132) 
        * Added implementation details for `cmark` and `cunmark` [\#217](https://github.com/AY2122S1-CS2103T-W15-3/tp/issues/217) 

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#33](https://github.com/AY2122S1-CS2103T-W15-3/tp/issues/33), [\#145](https://github.com/AY2122S1-CS2103T-W15-3/tp/issues/145), [\#148](https://github.com/AY2122S1-CS2103T-W15-3/tp/issues/148)

    * Reported bugs and suggestions for other teams in the class : [\#169](https://github.com/AY2122S1-CS2103T-T13-3/tp/issues/169), [\#178](https://github.com/AY2122S1-CS2103T-T13-3/tp/issues/178), [\#181](https://github.com/AY2122S1-CS2103T-T13-3/tp/issues/181)


© 2021 GitHub, Inc.
Terms
Privacy
Security
Status
Docs
Contact GitHub
Pricing
API
Training
Blog
About
