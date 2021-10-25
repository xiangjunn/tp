---
layout: page
title: SoConnect User Guide
---

SoConnect is a **desktop app for SoC students to manage contacts of Professors and Teaching Assistants, 
as well as to keep track of noteworthy events, optimized for use via a _Command Line Interface (CLI)_** while still having 
the benefits of a _Graphical User Interface (GUI)_. If you can type fast, SoConnect can get your contact management tasks
done faster than traditional GUI apps. 

This User Guide will help you to familiarise yourself with your SoConnect quickly and teach you the 
full range of features it offers.


* Table of Contents{:toc}

--------------------------------------------------------------------------------------------------------------------

## How to use SoConnect User Guide

* You can click on the titles in the Table of Contents to jump the section that you are interested in.
* You will find these icons in this user guide:
    * **:bulb: Tip** provides additional information that might be useful to you.
    * **:information_source: Note** provides supplementary information that helps you to understand this User Guide.
    * **:exclamation: Caution** cautions you with the undesirable effect that you will encounter under specific situations.
* You can find explanations of _italicised_ words in the [Glossary](#glossary).
* You can refer to the [Command Summary](#command-summary) for a complete overview of all SoConnect features and commands.

<div markdown="block" class="alert alert-info">

**:information_source: About the _Command Syntax_ format:**<br>

* Words in `UPPER_CASE` are the _parameters_ that you will provide.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* _Fields_ in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Fields with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family`, etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME [p/PHONE_NUMBER]`, `[p/PHONE_NUMBER] n/NAME` is also acceptable.

* If a parameter is expected only once in the command, but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help` and `exit`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* Date and time must follow dd-MM-yyyy HH:mm format.
  e.g. if the date and time is 1 May 2021 6.30pm, you should specify it as `01-05-2021 18:30`

</div>


## Quick start

1. Ensure you have [Java 11](https://www.oracle.com/java/technologies/downloads/) or above installed in your Computer.
   
   <div markdown="span" class="alert alert-primary">:bulb:**Tip:**
   
   [Here](https://www.java.com/en/download/help/version_manual.html) is how you can check the Java Version installed in your Computer.
   </div>

2. Download the latest `soconnect.jar` from [here](https://github.com/AY2122S1-CS2103T-W15-3/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your SoConnect app.

4. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)
   If SoConnect does not start by double-clicking, you can check this [alternative](#how-to-start-soconnect-using-command-prompt) to start it too.

5. Type the command in the command box and press Enter to execute it. 
   e.g. typing `help` and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `elist`: Lists all events.

   * `cadd n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`: Adds a contact named `John Doe` to SoConnect.

   * `cdelete 3` : Deletes the third contact shown in SoConnect.

   * `eclear` : Deletes all entries of events from SoConnect.

   * `exit` : Exits SoConnect.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

There are three main sections to SoConnect Features: 
[Contact Management](#contact-management), 
[Event Management](#event-management) and 
[General](#general).

For each feature, you are provided with:
* Function and Description of the feature
* Format of _Command Syntax_ of the feature
* Examples of some usages of the feature


## Contact Management

This section details all the features and commands available in SoConnect that can help you with managing your contacts:
* [Adding a contact](#adding-a-contact-cadd)
* [Clearing all contacts](#clearing-all-contacts-cclear)
* [Deleting a contact](#deleting-a-contact-cdelete)
* [Editing a contact](#editing-a-contact-cedit)
* [Finding contacts](#finding-contacts-cfind)
* [Listing all contacts](#listing-all-contacts-clist)
* [Viewing a contact](#viewing-a-contact-cview)


### Adding a contact: `cadd`

Adds a contact to SoConnect.

**Format:** `cadd n/NAME e/EMAIL [p/PHONE_NUMBER] [a/ADDRESS] [th/TELEGRAM_HANDLE] [z/ZOOM] [t/TAG]…`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A contact can have any number of tags (including 0)
</div>

**Examples:**
* `cadd n/Alex Doe e/e0123456@u.nus.edu a/COM1 #99-99 th/johnDoe99 t/Professor`
* `cadd n/ Jon Cheng t/TA e/e7654321@u.nus.edu a/COM1-0201 p/87654321 t/Senior th/jonnyjohnny z/https://nus-sg.zoom.us/j/0123456789?pwd=ABCDEFGHIJKLMNOPDJFHISDFSDH`


### Clearing all contacts: `cclear`

Clears all entries of contacts from SoConnect.

**Format:** `cclear`

<div markdown="span" class="alert alert-info">:information_source: **Note:**
This will not change the events saved in SoConnect. 
</div>


### Deleting a contact: `cdelete`

Deletes the specified contact from SoConnect.

**Format:** `cdelete INDEX1[-INDEX2]`

* Deletes the contact at the specified `INDEX1` or between the specified
  range from `INDEX1` to `INDEX2` inclusively.
* `INDEX` refers to the index number shown in the displayed contact list.

<div markdown="span" class="alert alert-info">:information_source:**Note:**

`INDEX` **must be a positive integer**. e.g. 1, 2, 3, …
</div>

**Examples:**
* `clist` followed by `cdelete 2` deletes the second contact from SoConnect.
* `cfind Betsy` followed by `cdelete 1` deletes the first contact from the results of the `cfind` command.
* `cdelete 3-5` deletes contacts from index 3 to 5 from SoConnect.


### Editing a contact: `cedit`

Edits an existing contact in SoConnect.

**Format:** `cedit INDEX [n/NAME] [e/EMAIL] [p/PHONE] [a/ADDRESS] [th/TELEGRAM_HANDLE] [z/ZOOM] [dt/TAG_DELETED]… [t/TAG_ADDED]… `

* Edits the contact at the specified `INDEX`.
* `INDEX` refers to the index number shown in the displayed contact list.
* You must provide at least one of the optional fields.
* Existing values will be updated to the input values.
* You can use `t/` to add a tag.
* You can remove a specific tag by typing `dt/` followed by the tag name that you wish to remove.
* You can remove all existing tags of a contact by typing `dt/*`.
* When editing tags, the tags to be deleted will be removed first, before new tags are added.

<div markdown="span" class="alert alert-info">:information_source:**Note:**

`INDEX` **must be a positive integer**. e.g. 1, 2, 3, …
</div>

**Examples:**
* `clist` followed by `cedit 2 p/91234567 e/agentX@thehightable.com` edits the phone number and email address of the second contact of SoConnect to be `91234567` and `agentX@thehightable.com` respectively.
* `cfind Betsy` followed by `cedit 2 n/Betsy Crower dt/*` edits the name of the second contact from the results of the `cfind` command to be `Betsy Crower` and clears all existing tags.
* `cedit 3 dt/TA`  deletes the `TA` tag from the third contact.
* `cedit 4 dt/*` deletes all tags from the fourth contact.
* `cedit 5 dt/classmate t/friend` first deletes the `classmate` tag, then adds `friend` tag to the fifth contact.


### Finding contacts: `cfind`

Finds all contacts with names that contain any of the given keywords.

**Format:** `cfind KEYWORD [MORE_KEYWORDS]`

* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`.
* Only the name is searched.
* Partial words can be matched e.g. `Han` will match `Hans`.
* Contact matching at least one keyword will be returned (i.e. _`OR` search_).
  e.g. `Hans Bo` will return `Hans Gruber` and `Bo Yang`.

<div markdown="span" class="alert alert-info">:information_source:**Note:**

The search by `cfind` is case-insensitive. e.g. `hans` will match `Hans`.
</div>

**Examples:**
* `cfind John` returns `john` and `Johnathon Doe`.
* `cfind alex david` returns `Alex Yeoh` and `David Li`.


### Listing all contacts: `clist`

Shows all contacts in the SoConnect, with all details by default.

**Format:** `clist [e/] [p/] [a/] [th/] [z/] [t/]`

* Names of contacts are always shown.
* When no optional fields are provided, e.g `clist`, all available details of each contact will be shown.
* When optional fields are provided, it will only show the names and the corresponding specified fields for each contact.
* You can provide more than one optional field.
* The order of the optional fields does not matter. e.g. both `clist e/ p/` and `clist p/ e/` will show only the names, email addresses and phone numbers of each contact.
* Fields of a contact that have no value will not appear.

**Examples:**
* `clist` shows all contacts in SoConnect with all available details for each contact.
* `clist e/ p/` shows all contacts in SoConnect with only their names, email addresses and phone numbers (if available).


### Viewing a contact: `cview`

Views a contact with all details fully shown.

**Format:** `cview INDEX`

* Views the contact at the specified `INDEX`.
* `INDEX` refers to the index number shown in the displayed contact list. 
* All truncations of the details of the contact you view will be expanded fully.

<div markdown="span" class="alert alert-info">:information_source:**Note:**

`INDEX` **must be a positive integer**. e.g. 1, 2, 3, …
</div>

**Examples:**
* `cview 2` shows all details of the second contact in SoConnect fully.
* `cfind alex david` followed by `cview 1` shows all details of the first contact from the result of the `cfind`.


## Event Management

This section details all the features and commands available in SoConnect that can help you with managing your events:
* [Adding an event](#adding-an-event-eadd)
* [Clearing all event](#clearing-all-events-eclear)
* [Deleting an event](#deleting-an-event-edelete)
* [Editing an event](#editing-an-event-eedit)
* [Finding events](#finding-events-efind)
* [Listing all events](#listing-all-events-elist)
* [Sorting events](#sorting-events-esort)
* [Viewing an event](#viewing-an-event-eview)



### Adding an event: `eadd`

Adds an event to SoConnect.

**Format:** `eadd n/NAME at/START_TIME [end/END_TIME] [d/DESCRIPTION] [a/ADDRESS] [z/ZOOM] [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
An event can have any number of tags (including 0)
</div>

<div markdown="span" class="alert alert-info">:information_source: **Note:**
Start time and End Time should be of format “dd-MM-yyyy HH:mm” (date-MONTH-year HOUR:minutes in 24-hour format).
</div>

**Examples:**
* `eadd n/Summer Party at/12-12-2021 15:12 a/123, Clementi Rd, 1234665 t/fun`
* `eadd n/CS2103T Lecture at/10-09-2021 16:00 end/18:00
  z/https://nus-sg.zoom.us/j/0123456789?pwd=ABCDEFGHIJKLMNOPDJFHISDFSDHk t/lecture`


### Clearing all events: `eclear`

Clears all entries of events from SoConnect.

**Format:** `eclear`

<div markdown="span" class="alert alert-primary">:bulb: **Note:**
This will not change the contacts saved in SoConnect. 
</div>


### Deleting an event: `edelete`

Deletes the specified event from SoConnect.

**Format:** `edelete INDEX1[-INDEX2]`

* Deletes the event at the specified `INDEX1` or between the specified range from `INDEX1` to `INDEX2` inclusively.
* `INDEX` refers to the index number shown in the displayed event list.

<div markdown="span" class="alert alert-info">:information_source:**Note:**

`INDEX` **must be a positive integer**. e.g. 1, 2, 3, …
</div>

**Examples:**
* `elist` followed by `edelete 2` deletes the second event from SoConnect.
* `efind Exam` followed by `edelete 1` deletes the first event from the results of the `efind` command.
* `edelete 3-5` deletes events from index 3 to 5 from SoConnect.


### Editing an event: `eedit`

Edits an existing event in SoConnect.

**Format:** `eedit INDEX [n/NAME] [at/START_TIME] [end/END_TIME] [d/DESCRIPTION] [a/ADDRESS] [z/ZOOM] [dt/TAG_DELETED]…​ [t/TAG_ADDED]…​`

* Edits the event at the specified `INDEX`.
* `INDEX` refers to the index number shown in the displayed event list.
* You must provide at least one of the optional fields.
* Existing values will be updated to the input values.
* You can use `t/` to add a tag.
* You can remove a specific tag by typing `dt/` followed by the tag name that you wish to remove.
* You can remove all existing tags of a contact by typing `dt/*`.
* When editing tags, the tags to be deleted will be removed first, before new tags are added.

<div markdown="span" class="alert alert-info">:information_source: **Note:** 
Start time and End Time should be of format “dd-MM-yyyy HH:mm” (date-MONTH-year HOUR:minutes in 24-hour format).
</div>

<div markdown="span" class="alert alert-info">:information_source:**Note:**

`INDEX` **must be a positive integer**. e.g. 1, 2, 3, …
</div>

**Examples:**
* `elist` followed by `eedit 2 n/CS2103T Exam dt/Easy_exams t/Hard_exams`
  first changes the name of the second event to `CS2103T Exam`, deletes the tag `Easy_exams` and
  adds the tag `Hard_exams`.
* `efind Betsy` followed by `eedit 2 n/Betsy’s Wedding` edits the name of the second event from the results of the `efind` command to be `Betsy’s Wedding`.
* `eedit 4 dt/*` deletes all existing tags from the fourth event.


### Finding Events: `efind`

Finds all events with names that contain any of the given keywords.

**Format:** `efind KEYWORD [MORE_KEYWORDS]`

* The order of the keywords does not matter. e.g. `Exam Hard` will match `Hard Exam`.
* Only the name is searched.
* Partial words can be matched e.g. `Exa` will match `CS2103T Exam` .
* Events matching at least one keyword will be returned (i.e. _`OR` search_).
  e.g. `Exam Hard` will return `Hard Exam`, `CS1101S Exams`.

<div markdown="span" class="alert alert-info">:information_source:**Note:**

The search by `efind` is case-insensitive. e.g. `exams` will match `Exams`.
</div>

**Examples:**
* `efind ex` returns `exams` and `Examinations`
* `efind CS Exam` returns `CS2100 Exam`,  `CS2101`


### Listing all events: `elist`

Shows all events in SoConnect, with all details by default.

**Format:** `elist [at/] [end/] [d/] [a/] [z/] [t/]`

* Names of events are always shown.
* When no optional fields are provided, e.g. `elist` , all available details of each event will be shown.
* When optional fields are provided, it will only show the names and the corresponding specified fields for each event.
* You can provide more than one optional field.
* The order of the optional fields does not matter. e.g. both `elist d/ at/` and `elist at/ d/` will only show the names, descriptions and starting times of each event.
* Fields of an event that have no value will no appear.

**Examples:**
* `elist` shows all events in SoConnect with all available details for each event.
* `elist d/ at/` events in SoConnect with only their names, starting times and descriptions (if available).


### Sorting events: `esort`

Sorts all events by start time and displays all upcoming or ongoing events.

**Format:** `esort`

<div markdown="span" class="alert alert-info">:information_source:**Note:**

Events that have ended will not be shown after you execute `esort`
</div>


### Viewing an event: `eview`

Views an event with all details fully shown.

**Format:** `eview INDEX`

* Views the event at the specified `INDEX`.
* `INDEX` refers to the index number shown in the displayed event list. 
* All truncations of the details of the event you view will be expanded fully.

<div markdown="span" class="alert alert-info">:information_source:**Note:**

`INDEX` **must be a positive integer**. e.g. 1, 2, 3, …
</div>

**Examples:**
* `eview 1` shows all details of the first event in SoConnect fully.
* `efind exam` followed by `eview 4` shows all details of the fourth event from the result of the `cfind`.


## General

This section details all other the features and commands available in SoConnect that can enhance your SoConnect experience:
* [Calendar Window](#calendar-window-calendar)
* [Exiting SoConnect](#exiting-soconnect-exit)
* [Help Window](#help-window-help)
* [Redo a command](#redo-a-command-redo) {Coming Soon}
* [Undo a command](#undo-a-command-undo) {Coming Soon}

### Calendar Window: `calendar`

Shows a calendar of all the events. 

**Format:** `calendar`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**

Alternatively, you can view the calendar using the top menu via `File -> Calendar` or press `F2`.
</div>

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
Any changes made in the calendar window will not be saved. 
Do not attempt to add new events using the calendar window. 
Doing so might result in a crash and your data may be lost.
</div>

![calendar](images/Calendar.png)


### Exiting SoConnect: `exit`

Exits and Closes SoConnect.

**Format:** `exit`


### Help Window: `help`

Shows a message explaining how to access the help page.

**Format:** `help`

![help message](images/helpMessage.png)


### Redo a command: `redo`

{Coming Soon}

Restores SoConnect to a previously undone state from its history.

**Format:** `redo`

**Examples:**
* `edelete 1` followed by `undo` restores the deleted event in the event list. This followed by `redo` command will delete the event at index 1 again.


### Undo a command: `undo`

{Coming Soon}

Restore SoConnect to its previous state from its history.

**Format:** `undo`

**Examples:**
* `edelete 1` followed by `undo` restores the deleted event in the event list.
* `add n/John Doe e/john@gmail.com` followed by `undo` removes the added contact from contact list.


--------------------------------------------------------------------------------------------------------------------

## SoConnect Saved Data

### Saving the data

SoConnect data is saved in the hard disk automatically after any command that changes the data. 
There is no need to save manually.

### Editing the data file

SoConnect data are saved as a _JSON file_ `[JAR file location]/data/soconnect.json`. 
Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, 
SoConnect will discard all data and start with an empty data file at the next run.
</div>

--------------------------------------------------------------------------------------------------------------------

## Others

### FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous SoConnect home folder.

### How to start SoConnect using Command Prompt

{to be added soon}

### Upcoming Features

Here are some of the features that are currently under progress. You will soon be able to:
* Find contacts and events by specific fields
* Link events and contacts together

--------------------------------------------------------------------------------------------------------------------

## Command Summary

**Contact Management**

Action | Format, Examples
--------|------------------
**[Add](#adding-a-contact-cadd)** | `cadd n/NAME e/EMAIL [p/PHONE_NUMBER] [a/ADDRESS] [th/TELEGRAM_HANDLE] [z/ZOOM] [t/TAG]…​` <br> e.g., `cadd n/James Ho p/22224444 e/hohohojames@u.nus.edu a/123, Clementi Rd, 1234665 t/Professor`
**[Clear](#clearing-all-contacts-cclear)** | `cclear`
**[Delete](#deleting-a-contact-cdelete)** | `cdelete INDEX1[-INDEX2]`<br> e.g. `cdelete 3` <br> e.g. `cdelete 1-5`
**[Edit](#editing-a-contact-cedit)** | `cedit INDEX [n/NAME] [e/EMAIL] [p/PHONE] [a/ADDRESS] [th/TELEGRAM_HANDLE] [z/ZOOM] [dt/TAG_DELETED]…​ [t/TAG_ADDED]…​​`<br> e.g.`cedit 2 n/James Lee e/jameslee@u.nus.edu p/91234567 dt/OP1_projectmate t/CS2103T_projectmate t/roommate` <br> e.g. `cedit 3 dt/*`
**[Find](#finding-contacts-cfind)** | `cfind KEYWORD [MORE_KEYWORDS]`<br> e.g. `cfind James Jake`
**[List](#listing-all-contacts-clist)** | `clist [e/] [p/] [a/] [th/] [z/] [t/]` <br> e.g. `clist` <br> e.g. `clist e/ p/`
**[View](#viewing-a-contact-cview)** | `cview INDEX`<br> e.g. `cview 3`

**Event Management**

Action | Format, Examples
--------|------------------
**[Add](#adding-an-event-eadd)** | `eadd n/NAME at/START_TIME [end/END_TIME] [d/DESCRIPTION] [a/ADDRESS] [z/ZOOM] [t/TAG]…​ ` <br> e.g., `eadd n/Summer Party at/12-12-2021 15:12 a/123, Clementi Rd, 1234665 t/fun`
**[Clear](#clearing-all-events-eclear)** | `eclear`
**[Delete](#deleting-an-event-edelete)** | `edelete INDEX`<br> e.g., `edelete 3` <br> e.g., `edelete 1-5`
**[Edit](#editing-an-event-eedit)** | `eedit INDEX [n/NAME] [at/START_TIME] [end/END_TIME] [d/DESCRIPTION] [a/ADDRESS] [z/ZOOM] [dt/TAG_DELETED]…​ [t/TAG_ADDED]…​`<br> e.g.,`eedit 2 n/CS2103T Exam dt/Easy_exams t/Hard_exams` <br> e.g., `eedit 3 dt/*`
**[Find](#finding-events-efind)** | `efind KEYWORD [MORE_KEYWORDS]`<br> e.g., `efind CS2103T Exams`
**[List](#listing-all-events-elist)** | `elist [at/] [end/] [d/] [a/] [z/] [t/]` <br> e.g., `elist` <br> e.g., `elist at/ d/`
**[Sort](#sorting-events-esort)** | `esort`
**[View](#viewing-an-event-eview)** | `eview INDEX`<br> e.g. `eview 1`

**General**

Action | Format, Examples
--------|------------------
**[Calendar](#calendar-window-calendar)** | `calendar`
**[Exit](#exiting-soconnect-exit)** | `exit`
**[Help](#help-window-help)** | `help`
**[Redo](#redo-a-command-redo)** {Coming Soon} | `redo`
**[Undo](#undo-a-command-undo)** {Coming Soon} | `undo`

________________________________________________________________________________________________________________

## Glossary
{to be added}
