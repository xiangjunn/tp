---
layout: page
title: Janice Chen's Project Portfolio Page
---

### Project: SoConnect

SoConnect is a **desktop app for SoC students to manage contacts of Professors and Teaching Assistants,
as well as to keep track of noteworthy events, optimized for use via a _Command Line Interface (CLI)_** while still having
the benefits of a _Graphical User Interface (GUI)_. 

This project is part of the [CS2103T](https://nus-cs2103-ay2122s1.github.io/website/) team project requirements for AY2021/2022 Semester 1.

Given below are my contributions to the project.

* **New Feature**: Added the `Event` model, together with the ability to clear, delete and finding events.
    * What it does: allows the user to save events that consists of details such as event date and time, description, address. 
    * Justification: This feature improves the product significantly because a user can save events next to the contact list using the same platform. This feature saves the trouble of the students from using multiple platforms to separately store the contacts and events.
    * Highlights: This enhancement affects certain existing commands and how new commands should be implemented, since the model manager will need to take in both `Contact` and `Event`. `Event` and `Contact` also owns different fields, which adds more complexity in how the commands should work.
   
* **New Feature**: Added the help window user interface
  * What it does: allows the user to view the command summary of SoConnect in a different visual interface. 
  * Justification: This feature improves the product significantly because a user (especially new user) might find it challenging to remember all commands provided by SoConnect.
  * Highlights: This enhancement requires the understanding of a third-party GUI framework.
  * Credits: Text wrapping feature of help window is adapted from [@author:James_D](https://stackoverflow.com/questions/22732013/javafx-tablecolumn-text-wrapping)

* **New Feature**: Viewing a specific contact or event
  * What it does: allows the user to view a specific contact or event with all details fully shown
  * Justification: This feature improves the product significantly because some information saved in the contact or event might be too long and the information will be truncated by default.
  * Highlights: This enhancement requires new implementation of how to filter out all the other contacts/ events using index only, while changing the GUI framework setting to wrap text when it is in View Mode. Corresponding changes to the GUI setting is also needed to automatically disable View mode.
  


* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=w15-3&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=zoom&tabAuthor=janjanchen&tabRepo=AY2122S1-CS2103T-W15-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&zA=janjanchen&zR=AY2122S1-CS2103T-W15-3%2Ftp%5Bmaster%5D&zACS=199.78947368421052&zS=2021-09-17&zFS=w15-3&zU=2021-11-06&zMG=false&zFTF=commit&zFGS=groupByRepos&zFR=false)



* **Project management**:
  * Added documentation for `How to use SoConnect User Guide`, `Overview of SoConnect` , `List of Prefixes` and `Glossary` [\#139](https://github.com/AY2122S1-CS2103T-W15-3/tp/issues/139) and [\#236](https://github.com/AY2122S1-CS2103T-W15-3/tp/pull/236) 
  * Added screenshots for User Guide
  * Changed logging and json file name [\#98](https://github.com/AY2122S1-CS2103T-W15-3/tp/issues/98)



* **Enhancements to existing features**:
  * Enable partial word search for events and contacts
    * What it does: allows the user to search information on SoConnect without the need to specify the keyword fully
    * Justification: This feature improves the product significantly because user does not need to know the full word they want to search for within the contact/ event list.



* **Documentation**:
    * User Guide:
        * Added documentation for the features `cadd`, `cedit`, `clist` and `cfind` [\#11](https://github.com/AY2122S1-CS2103T-W15-3/tp/issues/11)
        * Added documentation for the features `cview` and `eview` [\#78](https://github.com/AY2122S1-CS2103T-W15-3/tp/issues/78)
        * Added documentation for the features `help` [\#139](https://github.com/AY2122S1-CS2103T-W15-3/tp/issues/139)
        * Did cosmetic tweaks to the document.
    * Developer Guide:
        * Added implementation details of the `edelete` feature and `Model`[\#127](https://github.com/AY2122S1-CS2103T-W15-3/tp/issues/127)
        


* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#117](https://github.com/AY2122S1-CS2103T-W15-3/tp/pull/117)

