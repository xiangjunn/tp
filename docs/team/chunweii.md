---
layout: page
title: Chun Wei's Project Portfolio Page
---

### Project: SoConnect

SoConnect is a **desktop app for SoC students to manage contacts of Professors and Teaching Assistants,
as well as to keep track of noteworthy events, optimized for use via a _Command Line Interface (CLI)_** while still having
the benefits of a _Graphical User Interface (GUI)_. This project is part of the [CS2103T](https://nus-cs2103-ay2122s1.github.io/website/) team project requirements for AY2021/2022 Semester 1.

The non-exhaustive list given below are my contributions to the project. All features come with their respective unit tests.

* **New Feature**: Changed fields to `Contact` model (Previously known as `Person`).
  * What it does: allows the user to save details about the telegram handle and zoom meeting link of the saved contact, and allowing the user to omit certain details about the contact.
  * Justification: Given that the users are mainly School of Computing (SoC) students in NUS, the product would be significantly more relevant if it allows students to save details like telegram handles and Zoom meeting links of their friends, professors and tutors. Telegram and Zoom are widely used applications in NUS. Also, since students may not be able to obtain every single detail about a contact they want to save, for example, many tutors do not share their addresses and phone numbers. By making certain fields optional, our product becomes more suitable for SoC students to use.
  * Highlights: This enhancement affects how certain commands such as add or edit will work, since these commands do not accept optional fields, except for tags, in the original Address Book 3 implementation. 

* **New Feature**: Added the [calendar user interface](../DeveloperGuide.html#calendar-ui-feature).
  * What it does: Allows the user to view their saved events in a clean visual calendar user interface. An added feature is that while the calendar is open, the calendar will automatically update itself if the user make changes to the list of events.
  * Justification: This feature improves the product significantly because a user may find it difficult to plan their appointments by just looking at the list of events in the right event list panel.
  * Highlights: This enhancement requires the understanding of a third-party GUI framework. In addition, the implementation of the auto-update for the calendar is non-trivial, since a poor implementation can introduce coupling and turn the code ugly.
  * Credits: [CalendarFX](https://github.com/dlsc-software-consulting-gmbh/CalendarFX). Inspiration is from team projects from other teams in the past and [present](https://ay2122s1-cs2103t-f13-3.github.io/tp/).

* **New Feature**: Sorting and displaying upcoming events
  * What it does: Allows the user to sort their events based on start time and display events that are ongoing or occuring soon.
  * Justification: Having an unsorted list of events will make it difficult for the user to find out what is their next activity for the day or the week.
  * Highlights: This is more than just a simple sorting by date and time, since there is also a need to filter out old events. Furthermore, it can interfere with other commands that will alter the order of the list, such as `emark`.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=w15-3&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=chunweii&tabRepo=AY2122S1-CS2103T-W15-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

* **Project management**:
  * Set up project dashboard and project milestones on GitHub.
  * Oversaw the entire project as the team leader.
  * Adopted the [long-lived feature branch workflow](https://github.com/nus-cs2103-AY2122S1/forum/issues/325#issuecomment-946409090) in [v1.2b](https://github.com/AY2122S1-CS2103T-W15-3/tp/pull/90) and the feature branch workflow in v1.3 onwards, to improve efficiency of the team contributions while maintaining protection of the master branch.

* **Documentation**:
  * User Guide:
    * Added documentation for the features `calendar`([\#122](https://github.com/AY2122S1-CS2103T-W15-3/tp/pull/122)), and the instructions to use the user guide. ([\#236](https://github.com/AY2122S1-CS2103T-W15-3/tp/pull/236))
    * Add instructions on how to open the jar file in terminal. [\#162](https://github.com/AY2122S1-CS2103T-W15-3/tp/pull/162)
  * Developer Guide:
    * Added implementation details of the [calendar user interface](../DeveloperGuide.html#calendar-ui-feature) and heavily edited the [List Events feature](../DeveloperGuide.html#list-events-feature).
    * Updated the [UI design section](../DeveloperGuide.html#ui-component).
    * Added use case 11 in [#217](https://github.com/AY2122S1-CS2103T-W15-3/tp/pull/217).

* **Community**:
  * Notable PRs reviewed (with non-trivial review comments): [\#147](https://github.com/AY2122S1-CS2103T-W15-3/tp/pull/147), [\#135](https://github.com/AY2122S1-CS2103T-W15-3/tp/pull/135), [\#94](https://github.com/AY2122S1-CS2103T-W15-3/tp/pull/94). See others [here](https://github.com/AY2122S1-CS2103T-W15-3/tp/pulls?page=1&q=is%3Apr+is%3Aclosed+reviewed-by%3Achunweii).
  * Contributed ideas and code to improve code quality for undo/redo feature and bug fix: [\#219](https://github.com/AY2122S1-CS2103T-W15-3/tp/pull/219)
  * Contributed to forum discussions (examples: [1](https://github.com/nus-cs2103-AY2122S1/forum/issues/190#issuecomment-913172698), [2](https://github.com/nus-cs2103-AY2122S1/forum/issues/267#issuecomment-925130845))
  * Reported bugs and suggestions for other teams in the class (examples: [1](https://github.com/AY2122S1-CS2103T-T12-4/tp/issues/159), [2](https://github.com/AY2122S1-CS2103T-T12-4/tp/issues/163))

* **Tools**:
  * Integrated a third party framework (CalendarFX) to the project ([\#42](https://github.com/AY2122S1-CS2103T-W15-3/tp/pull/122))
