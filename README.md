EchoBot
=======

A simple twitter bot that echoes solicited requests.

If using or modifying this bot please adhere to: Automation rules and best practices
https://support.twitter.com/articles/76915-automation-rules-and-best-practices

Prerequisites:
-------------------------

* JDK 1.5 or higher
* Maven
* For aditional dependecny information refer to pom.xml
* A twitter developer account and application

Build Instructions:
-------------------------

Building an Executable Jar:
> mvn package

The excutable jar can be found at: ./target/TweetBot-1.0-SNAPSHOT.jar

Usage:
-------------------------

Modify twitter4j.properties and specify the information for your twitter application.
When generating your access token you must set the access level to: Read, write, and direct messages 

Example Usage - Running as an executable jar:

> java -jar target/TweetBot-1.0-SNAPSHOT.jar

Log information will be output to ./logs/error.log


License
-------------------------
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.