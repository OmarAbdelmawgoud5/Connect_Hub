Project Objective
The goal of this project is to create a functional social networking platform with the following features:

User Account Management: Signup, login, logout, and user status tracking.

Profile Management: Update profile details, view posts, and manage friends.

Content Creation: Create posts and stories.

Friend Management: Send/accept friend requests, block/remove friends, and view friend suggestions.

Newsfeed: Display posts, stories, and friend statuses.

Features
1. User Account Management
Backend (Java):

Signup, login, and logout functionalities.

Input validation (email format, required fields).

Password hashing using MessageDigest or java.security.

Track user status (online/offline).

Store user attributes in a file-based database (JSON format).

Frontend (Swing):

Signup and login forms with error handling.

Logout button to update user status.

Display user status on the newsfeed page.

2. Profile Management
Backend (Java):

Update profile details (profile photo, cover photo, bio, password).

Fetch and display user posts and friends list.

Frontend (Swing):

Profile management page for editing details.

Display user posts and friends list with online/offline status.

3. Content Creation (Posts and Stories)
Backend (Java):

Create posts (permanent) and stories (temporary, expire after 24 hours).

Store content attributes (contentId, authorId, content, timestamp).

Automatically delete expired stories.

Frontend (Swing):

Newsfeed section for creating posts and stories.

4. Friend Management
Backend (Java):

Send/accept/decline friend requests.

Suggest friends not in the user's friend list.

Block or remove friends.

Display friend statuses (online/offline).

Frontend (Swing):

Friend management interface for requests, suggestions, and blocking/removing friends.

Display friend statuses on the newsfeed page.

5. Newsfeed Page
Backend (Java):

Fetch and serve posts, stories, friend lists, and suggestions.

Frontend (Swing):

Display posts, stories, friend lists, and suggestions.

Content creation area and refresh button.

Security and Database Management
Security:

Passwords are hashed using secure techniques (e.g., SHA-256 or bcrypt).

Input validation for all forms.

Error handling for failed login attempts.

Database Management:

File-based database using JSON format.

Optimized file structures to minimize redundancy and improve access speed.

Relationships between users and content are maintained (e.g., linking user IDs to posts).
