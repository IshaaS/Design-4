// Time Complexity : O(1), O(m) for getNewsFeed
// Space Complexity : O(n*m)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this :no


// Your code here along with comments explaining your approach
// -Used hasmap to store userid as key and hashset of userid that it follows as value
// -used hashmap to store user id as key and list of all tweets user hashmap
// -Deisgned class for tweets where we save tweet id and created at time, which increase after every new tweet is created
// 1. now whenever a user follows someone, we add it it the hashset for that user in the hashmap(if does not exist create new)
// 2. whenever user unfollows someone, we remove it form the hashset for that user in the hashmap
// 3. whenever an user tweets, we create a new tween object and add it in the list of that user's HashMap
// 4. to get newsfeed, we find hashset of users the user follow and then list of tweets for each user,
// and we add the last 10 tweets of each user to the priority queue(min heap) and keep top 10 tweets with 
// maximum tweet created time and return it as a list.


class Twitter {
    HashMap<Integer,HashSet<Integer>> follows;
    HashMap<Integer,List<Tweet>> hasTweets;
    int tweets;
    public Twitter() {
        this.follows= new HashMap<>();
        this.hasTweets=new HashMap<>();
        this.tweets=0;
    }
    class Tweet{
        int tweetId;
        int createdAt;
        public Tweet(int id){
            this.tweetId= id;
            this.createdAt=tweets++;
        }
    }
    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        if(!hasTweets.containsKey(userId)){
            hasTweets.put(userId, new ArrayList<>());
        }
        hasTweets.get(userId).add(new Tweet(tweetId));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)->a.createdAt-b.createdAt);
        HashSet<Integer> followed = follows.get(userId);
        if(followed!=null){
            for(int user: followed){
                List<Tweet> tweetsF = hasTweets.get(user);
                if(tweetsF==null) continue;
                int size= tweetsF.size();
                for(int i=size-1; i>size-11&&i>=0; i--){
                    pq.add(tweetsF.get(i));
                    if(pq.size()>10) pq.poll();
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0,pq.poll().tweetId);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!follows.containsKey(followerId)){
            follows.put(followerId, new HashSet<>());
        }
        follows.get(followerId).add(followeeId);
        
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(follows.containsKey(followerId)&& followerId!=followeeId){
            follows.get(followerId).remove(followeeId);
        }
        
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */