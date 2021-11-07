---
layout: page
title: Gordon's Project Portfolio Page
---

### Project: SoConnect

SoConnect is a **desktop app for SoC students to manage contacts of Professors and Teaching Assistants, as well as to keep track of noteworthy events, optimized for use via a _Command Line Interface (CLI)_** while still having the benefits of a _Graphical User Interface (GUI)_. 

This project is part of the [CS2103T](https://nus-cs2103-ay2122s1.github.io/website/) team project requirements for AY2021/2022 Semester 1.

Given below are my contributions to the project.

* **New Feature**: Added the ability to bookmark contacts and events.
    * What it does: allows the user to mark one or more contacts / events at a time. The bookmarked events / contacts will appear at the top of the contact / event list.
    * Justification: This feature improves the product significantly because it saves the user time from having to scroll through the list to or using the find command to find contacts / events that they use frequently and the app should provide a convenient way to refer to them.
    * Highlights: This enhancement affects existing commands commands to be added in future. It required an in-depth analysis of design alternatives. The implementation too was challenging as it required changes to existing commands.

* **New Feature**: Added ability to unmark contacts and events.
   * What it does: allows the users to unmark one or more bookmarked contacts / events at a time. The newly unmarked events / contacts will appear after all bookmarked contacts / events in the list.
    * Justification: This feature improves the product significantly because it allows the user to keep the number of bookmarked contacts / events small and unmark contacts and events they no longer reference frequently, saves them time from having to look through a long list of bookmarked contacts / events.
    * Highlights: This enhancement affects existing commands commands to be added in future. It required an in-depth analysis of design alternatives. The implementation too was challenging as it required changes to existing commands.

* **New Feature**: Added ability to search events based on other fields 
   * What it does: allows the users to search for events based on other fields beside the event name.
    * Justification: This feature improves the product significantly because it gives user more freedom to search for events, they can for example search for events based on location or description.
    * Highlights: This enhancement affects existing commands commands to be added in future. It required an in-depth analysis of design alternatives. The implementation too was challenging as it required changes to existing commands.

* **New Feature**: Added ability to list only certain fields of events 
   * What it does: allows the users to list certain types of fields of each event. 
    * Justification: This feature improves the product significantly because it allows user to focus on certain fields of events they are interested in instead of looking at the entire list.
    * Highlights: This enhancement affects existing commands commands to be added in future. It required an in-depth analysis of design alternatives. The implementation too was challenging as it required changes to existing commands.

* **Code contributed**: [RepoSense link]()

* **Project management**:
    * Manage issue tracker, created issues, linked them to the relevant milestone, tags and project dashboard and assigned them to the relevant teammate based on the breakdown of work discussed in the team meeting, where possible, included task descriptions in the issue for easy reference.

* **Enhancements to existing features**:
    * Updated contact search features to allow users to find by other fields (Pull request [\#135])
    * Modified storage class to store events (Pull request [\#53])

* **Documentation**:
    * User Guide:
        * Added documentation for the features `eedit`, `efind` and `edelete` [\#29]()
        * Added screenshots for `cdelete`, `emark`, eunmark`, `elink` and `eunlink`, and updated screenshots for `, `cmark`, `cunmark`, `edelete`, `efind`, `elist` [\#273]()
        * Add a link to JSON format [\#273]() 
        * Reorganised features from basic to more advanced [\#273]() 
    * Developer Guide:
        * Added implementation details for `elist` [\#132]()
        * Added implementation details for `cmark`, `cunmark`, `emark`, `eunmark` [\#]()

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#33](), [\#145](), [\#148]()

    * Reported bugs and suggestions for other teams in the class (examples: [1]https://github.com/AY2122S1-CS2103T-T13-3/tp/issues/169), [2]https://github.com/AY2122S1-CS2103T-T13-3/tp/issues/178), [3]https://github.com/AY2122S1-CS2103T-T13-3/tp/issues/181))

* _{you can add/remove categories in the list above}_
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
