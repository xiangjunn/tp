---
layout: page
title: Gordon's Project Portfolio Page
---

### Project: AddressBook Level 3

AddressBook - Level 3 is a desktop address book application used for teaching Software Engineering principles. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to bookmark/unmark contacts and events.
    * What it does: allows the user to mark one or more contacts/events one at a time. The marked contacts/events will appear in reverse order to the order in which the user speified the indexes.
    * Justification: This feature improves the product significantly because this reduces the time the user has to scroll through the contacts/events to find a frequently used contact and the app should provide a convenient way for users to refer to contacts they more frequently use.
    * Highlights: This enhancement affects existing commands and commands to be added in future. It required an in-depth analysis of design alternatives. The implementation too midly challenging as while it is competible with existing commands in AB3, it required changes to other newly added commands like esort.
    * Credits: *{mention here if you reused any code/ideas from elsewhere or if a third-party library is heavily used in the feature so that a reader can make a more accurate judgement of how much effort went into the feature}*

* **New Feature**: Added the ability for storage to store events.
    * What it does: allows the storage component to process and store events created by the app when user adds a event.
    * Justification: This feature is important for the app as it allows the app to keep track of events that user adds or edits.
    * Highlights: This enhancement does not affect existing commands and commands to be added in future. It required an in-depth analysis of design alternatives. The implementation too midly challenging as while it requires an indept understanding of the storage class and how to refactor the current storage class to store events in addition to contacts without affecting its ability to store contacts.
    * Credits: *{mention here if you reused any code/ideas from elsewhere or if a third-party library is heavily used in the feature so that a reader can make a more accurate judgement of how much effort went into the feature}*

* **New Feature**: Added the ability to add events.
    * What it does: allows users to add an event to be tracked by the app. The newly added event will be added to the bottom of the event list.
    * Justification: This feature is essential to the app as part of the app's functionality of managing events.
    * Highlights: This enhancement does not affect existing commands and commands to be added in future. It required an in-depth analysis of design alternatives. 
      
* **New Feature**: Added the ability to edit events.
    * What it does: allows users to edit the different fields of an event being tracked by the app, it also allows users to delete all the existing tags of an event. The newly information provided by the user will be used to update the specified event.
    * Justification: This feature is essential to the app as part of the app's functionality of managing events.
    * Highlights: This enhancement does not affect existing commands and commands to be added in future. It required an in-depth analysis of design alternatives. 

* **New Feature**: Added the ability to list events.
    * What it does: allows users to list all events being tracked by the app. Users can specify certain fields to be displayed, by default, all available fields of all events      will be displayed.
    * Justification: This feature is essential to the app as part of the app's functionality of managing events.
    * Highlights: This enhancement does not affect existing commands and commands to be added in future. It required an in-depth analysis of design alternatives. 

* **Code contributed**: [RepoSense link]()

* **Project management**:
    * Managed releases `v1.3` - `v1.5rc` (3 releases) on GitHub

* **Enhancements to existing features**:
    * Modified  finding contacts feature (cfind), to allow users to find by certain field(s)    
    * Modified  (Pull requests [\#33](), [\#34]())
    * Wrote additional tests for existing features to increase coverage from 88% to 92% (Pull requests [\#36](), [\#38]())

* **Documentation**:
    * User Guide:
        * Added documentation for the features `delete` and `find` [\#72]()
        * Did cosmetic tweaks to existing documentation of features `clear`, `exit`: [\#74]()
    * Developer Guide:
        * Added implementation details of the `storage features`.

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#12](), [\#32](), [\#19](), [\#42]()
    * Contributed to forum discussions (examples: [1](), [2](), [3](), [4]())
    * Reported bugs and suggestions for other teams in the class (examples: [1](), [2](), [3]())
    * Some parts of the history feature I added was adopted by several other class mates ([1](), [2]())

* **Tools**:
    
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
