class Solution {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer,Integer>hm=new HashMap<>();
        int j=0;
        for(int i=0;i<nums.length;i++){
            hm.put(nums[i],j);
            j++;
        }
            for(int i=0;i<nums.length;i++){
                int a =target-nums[i];
                if(hm.containsKey(a)&&i!=hm.get(a)){
                    return new int[]{i,hm.get(a)};
                }
            }
             return new int[]{};
    }
}
