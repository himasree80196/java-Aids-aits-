class Solution {
    public int singleNumber(int[] nums) {
        Arrays.sort(nums);
        for(int i=0;i<nums.length;i++){
            if(i==nums.length-1){
                return nums[i];
            }
            if(nums[i]!=nums[i+1]){
                return nums[i];
        }else{
            i++;
        }
    }
    return 0;
}
}

