---
layout: page
title: User Guide
---

SoConnect is a **desktop app for SoC students to managing contacts of professors and teaching assistants, 
as well as to keep track of noteworthy events, optimized for use via a Command Line Interface** (CLI) while still having 
the benefits of a Graphical User Interface (GUI). If you can type fast, SoConnect can get your contact management tasks
done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `soconnect.jar` from [here](https://github.com/se-edu/addressbook-level3/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your SoConnect app.

4. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`elist`** : Lists all events.

   * **`cadd`**`n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the contact list.

   * **`cdelete`**`3` : Deletes the 3rd contact shown in the contact list.

   * **`eclear`** : Deletes all events.

   * **`exit`** : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family`, etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* Date and time must follow dd-MM-yyyy HH:mm format.

</div>

## Managing Contacts


### Adding a contact: `cadd`

Adds a contact to the contact list.

Format: `cadd n/NAME e/EMAIL [p/PHONE_NUMBER] [a/ADDRESS] [th/TELEGRAM_HANDLE] [z/ZOOM] [t/TAG]…`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A contact can have any number of tags (including 0)
</div>


Examples:
* `cadd n/Alex Doe e/e0123456@u.nus.edu a/COM1 #99-99 th/johnDoe99 t/Professor`
* `cadd n/ Jon Cheng t/TA e/e7654321@u.nus.edu a/COM1-0201 p/87654321 t/Senior th/jonnyjohnny z/https://nus-sg.zoom.us/j/0123456789?pwd=ABCDEFGHIJKLMNOPDJFHISDFSDH`


### Listing all contacts : `clist`

Shows a list of all contacts in the contact list, with all of their details by default.

Format: `clist [e] [p] [a] [th] [z] [t]`

* Returned list will always include names of all contacts in the contact list.
* When no optional fields are provided, e.g `clist`, the list will show all available details of all contacts in the contact list.
* When optional fields are provided, the list will only show the names of all contacts in the contact list and the corresponding fields specified by the user.
* More than one optional field can be provided.
* The order of the optional fields does not matter. e.g both `clist e p` and `clist p e` will return a list of only the names, email addresses and phone numbers of all contacts in the contact list.
* If the specified field has no value for certain contacts in the contact list, it will not show anything for that corresponding field for that particular contact.

Examples:
* `clist` returns a list of all contacts in the contact list with all the available details for each contact.
* `clist e p` returns a list of all contacts in the contact list, showing only their names, email addresses and phone numbers (if available).


### Editing a contact : `cedit`

Edits an existing contact in the contact list.

Format: `cedit INDEX [n/NAME] [e/EMAIL] [p/PHONE] [a/ADDRESS] [th/TELEGRAM_HANDLE] [z/ZOOM] [dt/TAG_DELETED]… [t/TAG_ADDED]… `

* Edits the contact at the specified `INDEX`. The index refers to the index number shown in the displayed contact list. The index **must be a positive integer** 1, 2, 3, …
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* You can use `t/` to add a tag.
* You can remove a specific tag by typing `dt/` with the tag.
* You can remove all the contact’s tags by typing `dt/*` without specifying any tags after it.
* When editing tags, the tags to be deleted will be removed first, before new tags are added.


Examples:
* `clist` followed by `cedit 2 p/91234567 e/agentX@thehightable.com` edits the phone number and email address of the 2nd contact of the contact list to be `91234567` and `agentX@thehightable.com` respectively.
* `cfind Betsy` followed by `cedit 2 n/Betsy Crower dt/*` edits the name of the 2nd contact from the results of the `cfind` command to be `Betsy Crower` and clears all existing tags.
* `cedit 3 dt/TA`  deletes the `TA` tag from the 3rd contact.
* `cedit 4 dt/*` deletes all tags from the 4th contact.
* `cedit 5 dt/classmate t/friend` first deletes the `classmate` tag, then adds `friend` tag from the 5th contact.


### Locating contacts by name: `cfind`

Finds contacts whose names contain any of the given keywords.

Format: `cfind KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`.
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`.
* Only the name is searched.
* Partial words can be matched e.g. `Han` will match `Hans`.
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber` and `Bo Yang`.

Examples:

* `cfind John` returns `john` and `Johnathon Doe`.
* `cfind alex david` returns `Alex Yeoh` and `David Li`.
  
### Deleting a contact : `cdelete`

Deletes the specified contact from the contact list.

Format: `cdelete INDEX1[-INDEX2]`

- Deletes the contact at the specified `INDEX1` or between the specified
  range from `INDEX1` to `INDEX2` inclusively.
- The index refers to the index number shown in the displayed contact list.
- The index **must be a positive integer** 1, 2, 3, …​

Examples:
- `clist` followed by `cdelete 2` deletes the 2nd contact from the contact list.
- `cfind Betsy` followed by `cdelete 1` deletes the 1st contact from the results of the `cfind` command.
- `cdelete 3-5` deletes people with index between 3 and 5 inclusively from the contact list.

### Clearing all contacts : `cclear`

Clears all entries of contacts from the contact list.

Format: `cclear`



## Managing Events

### Adding an event: `eadd`

Adds an event to the event list.

Format: `eadd n/NAME at/START_TIME [end/END_TIME] [d/DESCRIPTION] [a/ADDRESS] [z/ZOOM] [t/TAG]…​`

💡 **Tip:** An event can have any number of tags (including 0)

Take note of the following time format:

- Start time should be of format “dd-MM-yyyy HH:mm” (date-month-year Hour:minutes in 24 hr format).

Examples:

- `eadd n/Summer Party at/12-12-2021 15:12 a/123, Clementi Rd, 1234665 t/fun`
- `eadd n/CS2103T Lecture at/10-09-2021 16:00 end/18:00
  z/https://nus-sg.zoom.us/j/0123456789?pwd=ABCDEFGHIJKLMNOPDJFHISDFSDHk t/lecture`

### Listing all events: `elist`

Shows a list of all events in the event list, with all of their details by default.

Format: `elist [at] [end] [d] [a] [z] [t]`

- Returned list will always include names of all events in the event list.
- When no optional fields are provided, e.g `elist` , the list will show all available details of all events
  in the event list.
- When optional fields are provided, the list will only show the names of all events in the event list and
  the corresponding fields specified by the user.
- More than one optional field can be provided.
- The order of the optional fields does not matter. e.g both `elist d at` and `elist at d` will return a list
  of only the names, descriptions and starting times in the event list.
- If the specified field has no value for certain events in the event list, it will not show anything for
  that corresponding field for that particular event.

Examples:

- `elist` returns a list of all events in the event list with all the available details for each event.
- `elist d at` returns a list of all events in the event list, showing only their names, starting times and descriptions (if available).

### Editing an event : `eedit`

Edits an existing event in the event list.

Format: `eedit INDEX [n/NAME] [at/START_TIME] [end/END_TIME] [d/DESCRIPTION] [a/ADDRESS] [z/ZOOM] [dt/TAG_DELETED]…​ [t/TAG_ADDED]…​`


<div markdown="span" class="alert alert-info">
   
:information_source: **Note:** Start time should be of format “dd-MM-yyyy HH:mm” (date-month-year Hour:minutes in 24 hr format).
   
</div>

* `elist` followed by `eedit` 2 edits the 2nd event from the event list.
* Edits the event at the specified `INDEX`. `INDEX` refers to the index number shown in the displayed event list. `INDEX` **must be a positive integer** eg 1, 2, 3, …​
* **At least one** of the optional fields must be provided.
* Existing values will be updated to the input values.
* use `t/` to add a tag.
* use `dt/` to remove a tag.
* use `dt/*` to remove **all** tags
* When editing tags, the tags to be deleted will be removed first, before new tags are added

Examples:
* `elist` followed by `eedit 2 n/CS2103T Exam dt/Easy_exams t/Hard_exams` changes the name and tag of the 2nd event on the event list to `CS2103T Exam` and `Hard_exams` respectively.
* `efind Betsy` followed by `eedit 2 n/Betsy’s Wedding` edits the name of the 2nd event from the results of the `efind` command to be `Betsy’s Wedding`
* `eedit 4 dt/*` deletes all tags from the 4th event.

### Locating contacts by name : `efind`

Finds events which contain any of the given keywords.

Format: `efind KEYWORD [MORE_KEYWORDS]`

* **Case-insensitive**. e.g `exams` will match `Exams`
* Order of the keywords does not matter. e.g. `Exam Hard` will match `Hard Exam`
* Only the **name** is searched.
* Partial words can be matched e.g. `Exa` will match `CS2103T Exam` 
* Persons matching at least one keyword will be returned (i.e. `OR` search). e.g. `Exam Hard` will return `Hard Exam`, `CS1101S Exams`

Examples:
`efind ex` returns `exams` and `Examinations`
`efind CS Exam` returns `CS2100 Exam`,  `CS2101` 

### Deleting a contact : `edelete`

Deletes the specified event from the event list.

Format: `edelete INDEX1[-INDEX2]`

* Deletes the event at the specified `INDEX1` or between the specified range from `INDEX1` to `INDEX2` inclusive.
* `INDEX` refers to the index number shown in the displayed event list.
* `INDEX` **must be a positive integer**, eg 1, 2, 3, …​

Examples:
`elist` followed by `edelete 2` deletes the 2nd event from the event list.
`efind Exam` followed by `edelete 1` deletes the 1st event from the results of the `efind` command.
`edelete 3-5` deletes events with index between 3 and 5 inclusively from the event list.


### Clearing all events: `eclear`

Clears all entries of all events from the event list.

________________________________________________________________________________________________________________


## General

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Exiting the program : `exit`


**Exit** | `exit`


### Saving the data

SoConnect data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

SoConnect data are saved as a JSON file `[JAR file location]/data/soconnect.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, SoConnect will discard all data and start with an empty data file at the next run.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous SoConnect home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

### Managing Contacts

Action | Format, Examples
--------|------------------
**Add** | `cadd n/NAME e/EMAIL [p/PHONE_NUMBER] [a/ADDRESS] [th/TELEGRAM_HANDLE] [z/ZOOM] [t/TAG]…​` <br> e.g., `cadd n/James Ho p/22224444 e/hohohojames@u.nus.edu a/123, Clementi Rd, 1234665 t/Professor`
**Clear** | `cclear`
**Delete** | `cdelete INDEX1[-INDEX2]`<br> e.g., `cdelete 3` <br> e.g., `cdelete 1-5`
**Edit** | `cedit INDEX [n/NAME] [e/EMAIL] [p/PHONE] [a/ADDRESS] [th/TELEGRAM_HANDLE] [z/ZOOM] [dt/TAG_DELETED]…​ [t/TAG_ADDED]…​​`<br> e.g.,`cedit 2 n/James Lee e/jameslee@u.nus.edu p/91234567 dt/OP1_projectmate t/CS2103T_projectmate t/roommate` <br> e.g., `cedit 3 dt/*`
**Find** | `cfind KEYWORD [MORE_KEYWORDS]`<br> e.g., `cfind James Jake`
**List** | `clist [e] [p] [a] [th] [z] [t]` <br> e.g., `clist` <br> e.g., `clist e p`

### Managing Events

Action | Format, Examples
--------|------------------
**Add** | `eadd n/NAME at/START_TIME [end/END_TIME] [d/DESCRIPTION] [a/ADDRESS] [z/ZOOM] [t/TAG]…​ ` <br> e.g., `eadd n/Summer Party at/12-12-2021 15:12 a/123, Clementi Rd, 1234665 t/fun`
**Clear** | `eclear`
**Delete** | `edelete INDEX`<br> e.g., `edelete 3` <br> e.g., `edelete 1-5`
**Edit** | `eedit INDEX [n/NAME] [at/START_TIME] [end/END_TIME] [d/DESCRIPTION] [a/ADDRESS] [z/ZOOM] [dt/TAG_DELETED]…​ [t/TAG_ADDED]…​`<br> e.g.,`eedit 2 n/CS2103T Exam dt/Easy_exams t/Hard_exams` <br> e.g., `eedit 3 dt/*`
**Find** | `efind KEYWORD [MORE_KEYWORDS]`<br> e.g., `efind CS2103T Exams`
**List** | `elist [at] [end] [d] [a] [z] [t]` <br> e.g., `elist` <br> e.g., `elist at d`

________________________________________________________________________________________________________________

### General

Action | Format, Examples
--------|------------------
**Help** | `help`




