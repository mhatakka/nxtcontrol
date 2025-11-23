READ ME

Android Studio 3.2.1 project for lejOS LEGO NXT in Mac OSX 10.12 environment. 

Application is used for controlling LEGO NXT Mindstorm 2.0 hub over Bluetooth (classic). LEGO LCP -protocol is used
for sending commands to hub. To be continued ...


Start Working with Git & GitHub

1. On local machine go to Studio project folder
2. If not done, use:
   - 'git init'  to create .git -file (a repo is initialized)
3. Create .gitignore -file: 'touch .gitignore'
4. Create recommented content for .gitinignore -file when using Android-project
5. Add files to repo:
   - 'git add .'   adds all files except .gitignore cases
6. Make first commit:
    - 'git commit -m "Initial commit: Android Studio project" '
7. Create a new repo in GitHub -site
    - New ropository -> Name: 'nxtcontrol' -> Public/Private -> Create

8. Add GitHub repo as remote connection on local machine
   - 'git remote add origin https://github.com/YOUR_USERNAME/nxtcontrol.git'
9. Check remote connection on local machine
    - 'git remote -v'
    - Result ->  origin  https://github.com/YOUR_USERNAME/nxtcontrol.git (fetch)
             origin  https://github.com/YOUR_USERNAME/nxtcontrol.git (push)


GITHUB Fine-Grained Token 

1. Remove old credentials from Keystore on local machine project folder (nxtcontrol)
 
   - 'git credential-osxkeychain erase'
   host=github.com
   protocol=https

   - This makes sure Git does not use old password or token

  2. Create Fine-Grained token in GitHub

     - go: https://github.com/settings/personal-access-tokens
     - Choose: Fine-Grained -> Generate new token
     - Give token name f.ex.: nxcontrol-terminal-pushpull-YYYYMMDD
     - Resource owner: (your id)
    -  Permissions: Use  '+ Add permissions' -button  -> Contents -> Read & Write
    - Expiration: choose (30/60/90 days)
    - Push: 'Generate token' and copy the token (github_pat_....)

  3. Check Git remote in workstation

     - 'git remote -v'
    - result: https://github.com/your_id/nxtcontrol.git
    -  if shows SSH, change to HTTPS
    -  'git remote set-url origin https://github.com/YOUR_USERNAME/nxtcontrol.git'

  4. Set  credential helper for OSX (optional). This sets token to OSX Keychain.
     Now you don't have to write tokens when Password is asked

    -  'git config --global credential.helper osxkeychain'
	- Use 'Utilities -> KeyStore' (Lisäohjelmat -> Avainnipun käyttö) to check 

   5. Push files with Fine-Grained token to GitHub for the FIRST TIME (only)
      - 'git push -u origin main'
     -  Username: YOUR_USERNAME
     -  Password: (key/token)   (copy and past GitHub -token -> ENTER . Note! Key is not shown in terminal!!)

   6. Following push/pull -commands is used without giving your credentials!

       - Because the credential helper has been installed, Git retrieves the token from the KeyChain automatically!
       - 'git push'   when sending data
       - ('git pull')  when retrieving data

  Adding & Updating File(s) on Local Machine

  1. ADD: When a New File Is Added to Local Repo
     - 'git add  <filename>'  add when only one file
     -  'git add .'           add  when all new files
     -  
  2. Check status
     - 'git status'
     - Result: Changes to be committed:
     -    (  use "git reset HEAD <file>..." to unstage)
     -    new file:   <filename>
	
  4. Make a commit
     - 'git commit -m "Added a new file xxx"' write a commit message

  5. Send to cloud GitHub
     - 'git push'  The new file is sent to GitHub

  1. CHANGED FILE CONTENT: Changed file content of 'example.txt' file
     - 'git status'
     -   GIT -> Changed not staged for commit:
     -    (use "git add <file>..." to update what will be committed)
          
  2. Add the changed file !!
     - 'git add example.txt'
     - 
  4. Chech status again
     - 'git status'
     - GIT:  Changes to be committed:
     - (use "git reset HEAD <file>..." to unstage)
     - modified:   <path>/example.txt
	
  5. Send to cloud GitHub
     - 'git push'
  
 1. File UPDATED on GitHub cloud: 'fetch' info and the use 'status' to show the un-committed files
    - 'git fetch'  gets the data but does NOT combine it/those to local branch
    -     remote: Enumerating objects: 8, done.
    -     remote: Counting objects: 100% (8/8), done.
    -     remote: Compressing objects: 100% (6/6), done.
    -     remote: Total 6 (delta 4), reused 0 (delta 0), pack-reused 0 (from 0)
    -     Unpacking objects: 100% (6/6), done.
    -     From https://github.com/mhatakka/nxtcontrol
    - 'git status'   shows the status of the branch(es)
    -     On branch main
    -     Your branch is behind 'origin/main' by 2 commits, and can be fast-forwarded.
    -     (use "git pull" to update your local branch)
    - 'git log ..origin/main --oneline' shows NEW commits on GitHUB repo
    -     a81d8c1 Update README.md again
    -     a4ea728 Update README.md
    - 'git log origin/main.. --oneline' shows NEW commits on LOCAL repo (RESULT may be empty, use 'git status')
    - 'git diff origin/main' show differences on files between GitHub and local repo
    -      diff --git a/README.md b/README.md
    -      index 8c436c6..5d2675d 100644
    -       --- a/README.md
    -        +++ b/README.md
    -        @@ -6,106 +6,95 @@ Appl ... etc
    -   'git fetch origin'  For checking the changes. Loading the changes WITHOUT seeing/doing the merge
    -    and then use 'git diff origin/main' to see the differences. NOTHING has changes.
    -   'git pull'   does fetch + merge. The file content is UPDATED!


