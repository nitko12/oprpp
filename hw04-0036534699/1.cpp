#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

int dp[200005][2];

int recursion(vector<int> &v, int pos, int k, int n)
{

    if (v[pos] == 0 || v[pos] == n + 1)
    {
        if (k == 0)
        {
            return 1;
        }
        return 0;
    }

    if (dp[pos][k] != -1)
    {
        return dp[pos][k];
    }

    if (v[pos + 1] != v[pos] + 1)
    {
        if (k == 0)
        {
            return 1;
        }
        v.insert(v.begin() + pos + 1, v[pos] + 1);
        return dp[pos][k] = recursion(v, pos + 1, k - 1, n) + 1;
    }
    else if (v[pos - 1] != v[pos] - 1)
    {
        if (k == 0)
        {
            return 1;
        }
        v.insert(v.begin() + pos, v[pos] - 1);
        return dp[pos][k] = recursion(v, pos, k - 1, n) + 1;
    }

    return dp[pos][k] = recursion(v, pos + 1, k, n) + 1;
}

int longestChain(vector<int> &v, int k, int n)
{
    int l = v.size();
    int longestChain = 0;
    for (int i = 0; i < l; i++)
    {
        vector<int> tempvector(l);
        copy(v.begin(), v.end(), tempvector.begin());
        int temp = recursion(tempvector, i, k, n);
        if (temp > longestChain)
        {
            longestChain = temp;
        }
    }

    return longestChain;
}

int main()
{
    memset(dp, -1, sizeof(dp));

    int n, k, s;
    cin >> n >> k;
    vector<int> v;

    for (int i = 0; i < k; i++)
    {
        int tmp;
        cin >> tmp;
        v.push_back(tmp);
    }
    // s = 2;
    cin >> s;

    sort(v.begin(), v.end());

    cout << longestChain(v, s, n) << endl;

    return 0;
}